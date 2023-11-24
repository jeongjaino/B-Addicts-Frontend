package com.umc.addicts.data.model.auth

data class RegisterRequest (
    val id: String,
    val password: String,
    val nickname: String,
    val profileImage: ByteArray,
)