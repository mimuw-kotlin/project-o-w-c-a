package com.modules.db.DAO

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import com.modules.db.tables.SubjectsTable

class SubjectsDAO (id: EntityID<Int>) : IntEntity(id){

    companion object : IntEntityClass<SubjectsDAO>(SubjectsTable)

    var subject_code by SubjectsTable.subject_code
    var subject_name by SubjectsTable.subject_name
    var description by SubjectsTable.description
}