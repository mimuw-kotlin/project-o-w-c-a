package com.modules.db.tablesImpl

import com.modules.db.ConstsDB
import com.modules.db.UserTypes
import com.modules.db.tablesImpl.StudentsTable.default
import com.modules.db.tablesImpl.StudentsTable.references
import com.modules.db.tablesImpl.StudentsTable.uniqueIndex
import com.modules.db.tablesImpl.StudentsTable.varchar
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object TeachersTable : IntIdTable(ConstsDB.TEACHERS){
    val index = varchar(ConstsDB.INDEX, 8).uniqueIndex()
    val username = varchar(ConstsDB.USERNAME, 70)
    val user_type = varchar(ConstsDB.USER_TYPE, 20).default(UserTypes.getType(ConstsDB.TEACHER))
    val class_nbr = varchar(ConstsDB.CLASS_NBR, 3).references(ClassesTable.class_nbr,
        onDelete = ReferenceOption.CASCADE)
}