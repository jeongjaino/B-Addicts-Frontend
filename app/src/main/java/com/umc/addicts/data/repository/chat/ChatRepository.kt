package com.umc.addicts.data.repository.chat

import com.umc.addicts.data.model.chat.ChatRoom
import com.umc.addicts.data.model.chat.ChatRoomResponse

interface ChatRepository {
    suspend fun postChatRoom(chatRoom: ChatRoom): Result<Unit>

    suspend fun getChatRoomList(): Result<List<ChatRoomResponse>>
}