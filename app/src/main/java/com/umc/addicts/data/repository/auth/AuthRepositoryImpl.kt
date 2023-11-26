package com.umc.addicts.data.repository.auth

import com.umc.addicts.data.model.auth.LoginRequest
import com.umc.addicts.data.model.auth.LoginResponse
import com.umc.addicts.data.model.auth.RegisterProfileResponse
import com.umc.addicts.data.model.auth.RegisterRequest
import com.umc.addicts.data.remote.AuthService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
): AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Result<Unit> {
        return runCatching {
            service.registerInfo(registerRequest)
        }
    }

    override suspend fun registerProfile(imageByteArray: ByteArray): Result<RegisterProfileResponse> {
        return runCatching {
            val requestFile = imageByteArray.toRequestBody("image/*".toMediaTypeOrNull())
            service.registerProfile(
                file = MultipartBody.Part.createFormData(
                    name = "profile",
                    filename = "image.jpg",
                    requestFile
                )
            )
        }
    }

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return runCatching {
            service.login(loginRequest)
        }
    }
}