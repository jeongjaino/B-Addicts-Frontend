package com.umc.addicts.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.umc.addicts.data.remote.AuthInterceptor
import com.umc.addicts.data.remote.AuthService
import com.umc.addicts.data.remote.BoardService
import com.umc.addicts.data.remote.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        factory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://13.209.237.234:8080")
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideBoardService(
        retrofit: Retrofit
    ): BoardService = retrofit.create(BoardService::class.java)

    @Provides
    @Singleton
    fun provideChatService(
        retrofit: Retrofit
    ): ChatService = retrofit.create(ChatService::class.java)
}