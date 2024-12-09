package com.modules.db.dataModels

import kotlinx.serialization.Serializable

@Serializable
class SubjectModel(
    val subject_code: String,
    val subject_name: String,
    val description: String
)
