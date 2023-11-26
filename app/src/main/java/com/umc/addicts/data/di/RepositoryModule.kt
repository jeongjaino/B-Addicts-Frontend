package com.umc.addicts.data.di

import android.content.Context
import com.umc.addicts.data.remote.AuthService
import com.umc.addicts.data.remote.BoardService
import com.umc.addicts.data.remote.ChatService
import com.umc.addicts.data.repository.auth.AuthRepository
import com.umc.addicts.data.repository.auth.AuthRepositoryImpl
import com.umc.addicts.data.repository.auth.TokenRepository
import com.umc.addicts.data.repository.auth.TokenRepositoryImpl
import com.umc.addicts.data.repository.board.BoardRepository
import com.umc.addicts.data.repository.board.BoardRepositoryImpl
import com.umc.addicts.data.repository.chat.ChatRepository
import com.umc.addicts.data.repository.chat.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        service: AuthService
    ): AuthRepository = AuthRepositoryImpl(service)

    @Singleton
    @Provides
    fun providesBoardRepository(
        service: BoardService
    ): BoardRepository = BoardRepositoryImpl(service)

    @Singleton
    @Provides
    fun providesTokenRepository(
        @ApplicationContext context: Context
    ): TokenRepository = TokenRepositoryImpl(context)

    @Singleton
    @Provides
    fun providesChatRepository(
        service: ChatService
    ): ChatRepository = ChatRepositoryImpl(service)
}