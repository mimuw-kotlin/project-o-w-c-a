package com.modules.db.tables

import com.modules.db.other.ConstsDB
import org.jetbrains.exposed.dao.id.IntIdTable

object ClassesTable : IntIdTable(ConstsDB.CLASSES){
    val class_nbr = varchar(ConstsDB.CLASS_NBR, 3).uniqueIndex()
    val class_teacher_name = varchar(ConstsDB.CLASS_TEACHER_NAME, 70).default(ConstsDB.N_A)
}