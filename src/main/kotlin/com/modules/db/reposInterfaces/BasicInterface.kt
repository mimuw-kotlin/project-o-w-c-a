package com.modules.db.reposInterfaces

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


interface BasicInterface <ModelT> {
    suspend fun getAll(): List<ModelT>
    suspend fun getByIndex(index: String): ModelT?
    suspend fun removeByIndex(index: String): Boolean
    suspend fun addRow(newRow: ModelT)
}