package com.umc.addicts.data.repository.chat

import com.umc.addicts.data.model.chat.ChatRoom
import com.umc.addicts.data.model.chat.ChatRoomResponse
import com.umc.addicts.data.remote.ChatService
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val service: ChatService
): ChatRepository{
    override suspend fun postChatRoom(chatRoom: ChatRoom): Result<Unit> {
        return runCatching {
            service.postChatRoom(chatRoom)
        }
    }

    override suspend fun getChatRoomList(): Result<List<ChatRoomResponse>> {
        return runCatching {
            service.getChatRoom()
        }
    }
}