package com.modules.db.repos

import com.modules.db.DAO.PasswordsDAO
import com.modules.db.dataModels.PasswordModel
import com.modules.db.other.PasswordUtils
import com.modules.db.other.PswdCheckRetVal
import com.modules.db.passwordDAOToModel
import com.modules.db.reposInterfaces.PasswordInterface
import com.modules.db.suspendTransaction
import com.modules.db.tables.PasswordsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PasswordRepo : PasswordInterface {
    override suspend fun checkPassword(username: String, password: String): PswdCheckRetVal = suspendTransaction{

        val details = PasswordsDAO
            .find { (PasswordsTable.username eq username) }
            .map(::passwordDAOToModel)
            .firstOrNull()

        if (details == null)
        {
            println("LOG: pswdRepo.checkPassword: user not found")
            return@suspendTransaction PswdCheckRetVal.USER_NOT_FOUND
        }
        val hashedPassword = PasswordUtils.hashPassword(password)
        println("LOG: pswdRepo.checkPassword: hashedPassword: $hashedPassword")
        val retCheckVal = PasswordUtils.verifyPassword(password, details.password)

        if (retCheckVal)
            return@suspendTransaction PswdCheckRetVal.PASSWORD_CORRECT

        return@suspendTransaction PswdCheckRetVal.PASSWORD_INCORRECT
    }

    override suspend fun setPassword(username: String, password: String) = suspendTransaction{
        val hashedPassword = PasswordUtils.hashPassword(password)
        val user = PasswordsDAO.new {
            this.username = username
            this.password = hashedPassword
        }
    }
}