package com.umc.addicts.data.remote

import com.umc.addicts.data.model.chat.ChatRoom
import com.umc.addicts.data.model.chat.ChatRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatService {

    @POST("/chat-rooms")
    suspend fun postChatRoom(
        @Body chatRoom: ChatRoom
    )

    @GET("/chat-rooms")
    suspend fun getChatRoom(): List<ChatRoomResponse>
}