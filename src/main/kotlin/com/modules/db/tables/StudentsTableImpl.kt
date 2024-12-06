package com.modules.db.tables

import com.modules.db.tablesInterfaces.SchoolUserInterface

class StudentsTableImpl : SchoolUserInterface {
    override suspend fun getAll() {
        TODO("Not yet implemented")
    }

    override suspend fun getByIndex(index: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getByName(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getByType(userType: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeByIndex(index: String) {
        TODO("Not yet implemented")
    }

    override suspend fun <T> addUser(user: T) {
        TODO("Not yet implemented")
    }
}