package com.umc.addicts.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val userId: String,
    val nickname: String,
    val password: String,
    val image: String,
    val toxics: List<Toxic>,
)

@Serializable
data class Toxic(
    val keyword: String,
    val degree: String
)