package com.umc.addicts.data.remote

import com.umc.addicts.data.repository.auth.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = when (val accessToken = tokenRepository.accessToken) {
            "" -> {
                chain.request()
                    .newBuilder()
                    .build()
            }
            else -> {
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", accessToken)
                    .build()
            }
        }
        return chain.proceed(authRequest)
    }
}