package com.modules.db.reposInterfaces

enum class PasswordCheckRetVal {
    USER_NOT_FOUND,
    PASSWORD_INCORRECT,
    PASSWORD_CORRECT
}

interface PasswordInterface {
    suspend fun checkPassword(username: String, password: String): PasswordCheckRetVal
    suspend fun setPassword(username: String, password: String)
}