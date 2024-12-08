package com.modules.db.repos

import com.modules.db.DAO.PasswordsDAO
import com.modules.db.dataModels.PasswordModel
import com.modules.db.passwordDAOToModel
import com.modules.db.reposInterfaces.PasswordCheckRetVal
import com.modules.db.reposInterfaces.PasswordInterface
import com.modules.db.suspendTransaction
import com.modules.db.tables.PasswordsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.mindrot.jbcrypt.BCrypt

object PasswordUtils {
    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}


class PasswordRepo : PasswordInterface {
    override suspend fun checkPassword(username: String, password: String): PasswordCheckRetVal = suspendTransaction{
        val details = PasswordsDAO
            .find { (PasswordsTable.username eq username) }
            .map(::passwordDAOToModel)
            .firstOrNull()
        if (details == null) {
            return@suspendTransaction PasswordCheckRetVal.USER_NOT_FOUND
        }

        val retCheckVal = PasswordUtils.verifyPassword(password, details.password)

        if (retCheckVal) {
            return@suspendTransaction PasswordCheckRetVal.PASSWORD_CORRECT
        }
        return@suspendTransaction PasswordCheckRetVal.PASSWORD_INCORRECT
    }

    override suspend fun setPassword(username: String, password: String) = suspendTransaction{
        val hashedPassword = PasswordUtils.hashPassword(password)
        val user = PasswordsDAO.new {
            this.username = username
            this.password = hashedPassword
        }
    }
}