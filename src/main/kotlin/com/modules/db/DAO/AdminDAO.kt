package com.modules.db.DAO

import com.modules.db.tables.AdminTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AdminDAO(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<AdminDAO>(AdminTable)

    val username by AdminTable.username
    val password by AdminTable.password
    val user_type by AdminTable.user_type
}