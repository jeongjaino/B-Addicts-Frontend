package com.umc.addicts.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest (
    val userId: String,
    val password: String
)