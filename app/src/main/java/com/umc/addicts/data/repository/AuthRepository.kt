package com.umc.addicts.data.repository

import com.umc.addicts.data.model.auth.LoginRequest
import com.umc.addicts.data.model.auth.RegisterRequest

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest) : Result<Unit>


    suspend fun login(loginRequest: LoginRequest) : Result<Unit>
}