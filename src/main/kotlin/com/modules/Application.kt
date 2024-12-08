package com.modules

import com.modules.db.repos.PasswordRepo
import com.modules.db.repos.StudentRepo
import com.modules.db.repos.TeacherRepo
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val studentRepo = StudentRepo()
    val teacherRepo = TeacherRepo()
    val passwordRepo = PasswordRepo()
    val adminRepo = AdminRepo()

    configureSockets()
    configureSerialization()
    configureDatabases(environment.config)
    configureTemplating()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
