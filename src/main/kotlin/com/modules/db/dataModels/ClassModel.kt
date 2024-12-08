package com.modules.db.dataModels

import kotlinx.serialization.Serializable

@Serializable
data class ClassModel(
    val class_nbr: String,
    val class_teacher_name: String,
)
