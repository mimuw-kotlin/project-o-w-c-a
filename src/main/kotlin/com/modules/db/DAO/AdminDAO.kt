package com.modules.db.DAO

import com.modules.db.dataModels.AdminModel
import com.modules.db.tables.AdminTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AdminDAO(id: EntityID<Int>) : IntEntity(id){
    companion object CompObj : IntEntityClass<AdminDAO>(AdminTable)

    var username by AdminTable.username
    var password by AdminTable.password
    var user_type by AdminTable.user_type
}