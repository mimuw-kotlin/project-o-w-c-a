package com.modules.db.dataModels
import kotlinx.serialization.Serializable

@Serializable
data class AdminModel(
    val index: Int,
    val username: String,
    val password: String,
    val user_type: String
)
