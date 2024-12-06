package com.modules.db.tablesImpl

import com.modules.db.ConstsDB
import org.jetbrains.exposed.dao.id.IntIdTable

object SubjectsTable : IntIdTable(ConstsDB.SUBJECTS){
    val subject_name = varchar(ConstsDB.SUBJECT_NAME, 70)
    val description = varchar(ConstsDB.DESCRIPTION, 555).default(ConstsDB.NO_DESCR_AVAILABLE)
}