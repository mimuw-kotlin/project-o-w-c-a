package com.modules.db.reposInterfaces

interface SchoolUsersInterface<ModelT> : BasicInterface<ModelT> {

    suspend fun getByClassNbr(clsNbr: String): List<ModelT>

    suspend fun getByUsername(username: String): ModelT?
}

