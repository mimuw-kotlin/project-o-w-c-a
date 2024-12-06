package com.modules

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSockets()
    configureSerialization()
    configureDatabases(environment.config)
    configureTemplating()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
