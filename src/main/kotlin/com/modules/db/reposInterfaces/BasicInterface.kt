package com.modules.db.reposInterfaces

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


interface BasicInterface <Class> {
    suspend fun getAll(): List<Class>
    suspend fun getByIndex(index: String)
    suspend fun removeByIndex(index: String)
    suspend fun <T> addRow(newRow: T)
}