package com.modules.db.tablesImpl

import com.modules.db.ConstsDB
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull

object ClassesTable : IntIdTable(ConstsDB.CLASSES){
    val class_nbr = varchar(ConstsDB.CLASS_NBR, 3).uniqueIndex()
    val class_teacher_name = varchar(ConstsDB.CLASS_TEACHER_NAME, 70)
}