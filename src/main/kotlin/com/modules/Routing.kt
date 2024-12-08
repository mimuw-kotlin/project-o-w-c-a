package com.modules

import com.modules.db.dataModels.StudentModel
import com.modules.db.dataModels.TeacherModel
import com.modules.db.other.ConstsDB
import com.modules.db.other.UserTypes
import com.modules.db.repos.PasswordRepo
import com.modules.db.repos.StudentRepo
import com.modules.db.repos.TeacherRepo
import com.modules.db.reposInterfaces.SchoolUsersInterface
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
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import kotlinx.html.*
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


fun generateRandomString(length: Int = 8): String {
    val chars = "0123456789"
    return (1..length)
        .map { chars.random() }
        .joinToString("")
}

fun Application.configureRouting(studentRepo: StudentRepo,
                                 teacherRepo: TeacherRepo,
                                 passwordRepo: PasswordRepo) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        staticResources("/static", "static")

        get("/") {
            call.respondHtml {
                body {
                    h1 {
                        "Dziennik"
                    }
                    a("/loginForm") {
                        +"Login"
                    }
                    h2 {
                        +"Rejestracja:"
                    }
                    a("/register/student") {
                        +"Rejestracja ucznia"

                    }
                    br {  }
                    a("/register/teacher") {
                        +"Rejestracja nauczyciela"

                    }
                }
            }
        }

        get("/loginForm") {
            val existingSession = call.sessions.get<UserSession>()

            if (existingSession != null)
            {
                call.respondText("Already logged in as ${existingSession.username}.")
            }

            call.respondHtml {
                body {
                    form(
                        action = "/login",
                        encType = FormEncType.applicationXWwwFormUrlEncoded,
                        method = FormMethod.post
                    ) {
                        p {
                            +"Username:"
                            textInput(name = "username")
                        }
                        p {
                            +"Password:"
                            passwordInput(name = "password")
                        }
                        p {
                            submitInput() { value = "Login" }
                        }
                    }
                }
            }
        }

        route("/register") {

            get("/student") {
                val existingSession = call.sessions.get<UserSession>()

                if (existingSession != null)
                {
                    call.respondText("Already logged in as ${existingSession.username}.")
                }

                call.respondHtml {
                    body {
                        form(
                            action = "/register/student",
                            encType = FormEncType.applicationXWwwFormUrlEncoded,
                            method = FormMethod.post
                        ) {
                            p {
                                +"Username:"
                                textInput(name = "username")
                            }
                            p {
                                +"Password:"
                                passwordInput(name = "password")
                            }
                            p {
                                submitInput() { value = "Register" }
                            }
                        }
                    }
                }
            }

            post("/student") {
                val post = call.receiveParameters()
                val username = post["username"]
                val password = post["password"]

                if (username != null && password != null)
                {
                    studentRepo.addRow(StudentModel(
                                        index = generateRandomString(),
                                        username=username,
                                        user_type = UserTypes.getType(ConstsDB.STUDENT),
                                        class_nbr = "1E"
                                    )
                    )
                    passwordRepo.setPassword(username, password)
                    call.respondText ( "Registered student $username." )
                }
                else
                {
                    call.respondText("Invalid username or password.")
                }
            }

            get("/teacher") {
                val existingSession = call.sessions.get<UserSession>()

                if (existingSession != null)
                {
                    call.respondText("Already logged in as ${existingSession.username}.")
                }
                call.respondHtml {
                    body {
                        form(
                            action = "/register/teacher",
                            encType = FormEncType.applicationXWwwFormUrlEncoded,
                            method = FormMethod.post
                        ) {
                            p {
                                +"Username:"
                                textInput(name = "username")
                            }
                            p {
                                +"Password:"
                                passwordInput(name = "password")
                            }
                            p {
                                submitInput() { value = "Register" }
                            }
                        }
                    }
                }
            }

            post("/teacher") {
                val post = call.receiveParameters()
                val username = post["username"]
                val password = post["password"]

                if (username != null && password != null)
                {
                    teacherRepo.addRow(
                        TeacherModel(
                        index = generateRandomString(),
                        username=username,
                        user_type = UserTypes.getType(ConstsDB.TEACHER),
                        class_nbr = "1E"
                    )
                    )
                    passwordRepo.setPassword(username, password)
                    call.respondText ( "Registered teacher $username." )
                }
                else
                {
                    call.respondText("Invalid username or password.")
                }
            }
        }
    }
}
