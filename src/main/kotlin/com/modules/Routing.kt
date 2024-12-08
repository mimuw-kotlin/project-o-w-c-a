package com.modules

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

fun Application.configureRouting() {
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
                        "Rejestracja:"
                    }
                    a("/registerStudent") {
                        +"Rejestracja ucznia"

                    }
                    a("/registerTeacher") {
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

        get("/registerStudent") {
            val existingSession = call.sessions.get<UserSession>()

            if (existingSession != null)
            {
                call.respondText("Already logged in as ${existingSession.username}.")
            }

            call.respondHtml {
                body {
                    form(
                        action = "/registerStudent",
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

        get("/registerTeacher") {
            val existingSession = call.sessions.get<UserSession>()

            if (existingSession != null)
            {
                call.respondText("Already logged in as ${existingSession.username}.")
            }
            call.respondHtml {
                body {
                    form(
                        action = "/registerTeacher",
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
    }
}
