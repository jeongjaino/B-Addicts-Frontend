package com.umc.addicts.data.remote

import com.umc.addicts.data.model.auth.LoginRequest
import com.umc.addicts.data.model.auth.LoginResponse
import com.umc.addicts.data.model.auth.RegisterProfileResponse
import com.umc.addicts.data.model.auth.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {

    @POST("/users/profile")
    @Multipart
    suspend fun registerProfile(
        @Part file: MultipartBody.Part,
    ): RegisterProfileResponse

    @POST("/users/signup")
    suspend fun registerInfo(
        @Body registerRequest: RegisterRequest
    )

    @POST("/users/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : LoginResponse
}