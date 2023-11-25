package com.umc.addicts.data.repository.auth

interface TokenRepository {
    var accessToken: String
    fun clearToken()
}