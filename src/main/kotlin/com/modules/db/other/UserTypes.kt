package com.modules.db.other

// Only admin can add new user types
object UserTypes {
    private val allowedTypes = mutableSetOf(
                                ConstsDB.STUDENT,
                                ConstsDB.TEACHER,
                                ConstsDB.HEADMASTER,
                                ConstsDB.ADMIN)

    fun getAllTypes(): Set<String> {
        val mutableList = mutableListOf<String>()
        for (type in allowedTypes) {
            mutableList.add(type)
        }
        return mutableList.toSet()
    }

    fun getType(type: String): String {
        if (type.lowercase() in allowedTypes) {
            return type.lowercase()
        }
        return ConstsDB.N_A
    }

    fun isAllowedType(type: String): Boolean {
        return allowedTypes.contains(type)
    }

    fun addUserType(type: String) {
        allowedTypes.add(type.lowercase())
    }
}