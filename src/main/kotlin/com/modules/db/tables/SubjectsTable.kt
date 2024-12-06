package com.modules.db.tables

import com.modules.db.ConstsDB
import org.jetbrains.exposed.dao.id.IntIdTable

object SubjectsTable : IntIdTable(ConstsDB.SUBJECTS){
    val subject_code = varchar(ConstsDB.SUBJECT_CODE, 10).uniqueIndex()
    val subject_name = varchar(ConstsDB.SUBJECT_NAME, 70)
    val description = varchar(ConstsDB.DESCRIPTION, 555).default(ConstsDB.NO_DESCR_AVAILABLE)
}