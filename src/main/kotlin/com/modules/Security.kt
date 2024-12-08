package com.modules

import com.modules.db.other.ConstsDB
import com.modules.db.other.PswdCheckRetVal
import com.modules.db.other.UserTypes
import com.modules.db.repos.AdminRepo
import com.modules.db.repos.PasswordRepo
import com.modules.db.repos.StudentRepo
import com.modules.db.repos.TeacherRepo
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import java.sql.Connection
import java.sql.DriverManager
import java.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

// Username value can be either username or index - currently only username is supported
@Serializable
data class UserSession(val username: String, val password: String, val userType: String)

suspend fun checkPassword(pswdRepo: PasswordRepo, username: String, password: String): Boolean {
    when (pswdRepo.checkPassword(username, password)) {
        PswdCheckRetVal.USER_NOT_FOUND -> false
        PswdCheckRetVal.PASSWORD_INCORRECT -> false
        PswdCheckRetVal.PASSWORD_CORRECT -> true
    }
    return false
}

suspend fun checkUserType(
    username: String,
    teacherRepo: TeacherRepo,
    studentRepo: StudentRepo,
    adminRepo: AdminRepo ): String {
    // We make three queries to the db, which is not optimal
    // we should make a join query instead, but current impl of repos
    // does not support it
    if (adminRepo.getByUsername(username) != null)
        return UserTypes.getType(ConstsDB.ADMIN)
    if (teacherRepo.getByUsername(username) != null)
        return UserTypes.getType(ConstsDB.TEACHER)
    if (studentRepo.getByUsername(username) != null)
        return UserTypes.getType(ConstsDB.STUDENT)

    return ConstsDB.N_A
}


fun Application.configureSecurity(pswdRepo: PasswordRepo,
                                  teacherRepo: TeacherRepo,
                                  studentRepo: StudentRepo,
                                  adminRepo: AdminRepo) {
    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 120
        }
    }

    authentication {
        val myRealm = "MyRealm"
        val usersInMyRealmToHA1: Map<String, ByteArray> = mapOf(
            // pass="test", HA1=MD5("test:MyRealm:pass")="fb12475e62dedc5c2744d98eb73b8877"
            "test" to hex("fb12475e62dedc5c2744d98eb73b8877")
        )
    
        digest("myDigestAuth") {
            digestProvider { userName, realm ->
                usersInMyRealmToHA1[userName]
            }
        }
    }

    authentication {

        session<UserSession>("auth-session") {
            validate { session ->
                if (checkPassword(pswdRepo, session.username, session.password))
                    return@validate session
                else
                    return@validate null
            }

            challenge {
                call.respondRedirect("/loginForm")
            }
        }


        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                checkPassword(pswdRepo, credentials.name, credentials.password)
            }
        }

        form(name = "login-form-auth") {
            userParamName = "username"
            passwordParamName = "password"

            validate { credentials ->
                if (checkPassword(pswdRepo, credentials.name, credentials.password))
                    UserIdPrincipal(credentials.name)
                else
                {
                    AuthenticationFailedCause.InvalidCredentials
                    null
                }
            }

            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }
    }
    routing {

        authenticate("login-form-auth") {
            post("/login"){

                val existingSession = call.sessions.get<UserSession>()

                if (existingSession != null)
                {
                    call.respondText("Already logged in as ${existingSession.username}.")
                }
                else
                {
                    val userName = call.principal<UserIdPrincipal>()?.name.toString()
                    val userType = checkUserType(userName, teacherRepo, studentRepo, adminRepo)
                    val password = call.parameters["password"].toString()
                    call.sessions.set(UserSession(userName,
                        password,
                        userType))
                    call.respondText("Logged in as $userName.")
                }
            }
        }

        authenticate("auth-session") {
            get("/protected/session") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
    }
}
