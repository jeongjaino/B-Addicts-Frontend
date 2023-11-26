package com.umc.addicts.data.repository.auth

import com.umc.addicts.data.model.auth.LoginRequest
import com.umc.addicts.data.model.auth.LoginResponse
import com.umc.addicts.data.model.auth.RegisterProfileResponse
import com.umc.addicts.data.model.auth.RegisterRequest

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest) : Result<Unit>

    suspend fun registerProfile(imageByteArray: ByteArray): Result<RegisterProfileResponse>

    suspend fun login(loginRequest: LoginRequest) : Result<LoginResponse>
}