package com.umc.addicts.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class Chat (
    val content: String,
    val chatRoomId: Long,
    val userId: Long,
)