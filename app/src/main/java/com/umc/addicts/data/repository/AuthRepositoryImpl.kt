package com.umc.addicts.data.repository

import com.umc.addicts.data.model.auth.LoginRequest
import com.umc.addicts.data.model.auth.RegisterRequest
import com.umc.addicts.data.remote.AuthService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import kotlin.math.log

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService
): AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Result<Unit> {
        return runCatching {
            val requestFile = registerRequest
                .profileImage
                .toRequestBody("image/*".toMediaTypeOrNull())
            service.register(
                nickname = registerRequest.nickname.toRequestBody("text/plain".toMediaTypeOrNull()),
                file = MultipartBody.Part.createFormData(
                    name = "file",
                    filename = "image.jpg",
                    requestFile
                )
            )
        }
    }

    override suspend fun login(loginRequest: LoginRequest) : Result<Unit> {
        return runCatching {
            service.login(
                id = loginRequest.id,
                password = loginRequest.password
            )
        }
    }
}