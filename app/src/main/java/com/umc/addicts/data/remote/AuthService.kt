package com.umc.addicts.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {

    @POST("")
    suspend fun register(
        @Part("Nickname") nickname: RequestBody,
        @Part file: MultipartBody.Part
    )

    @GET("")
    suspend fun login(
        id: String,
        password: String
    )
}