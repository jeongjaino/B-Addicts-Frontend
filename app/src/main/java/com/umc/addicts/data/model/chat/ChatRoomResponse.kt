package com.umc.addicts.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse (
    val title: String,
    val keyword: String,
    val memberNum: Int,
    val deadline: String,
    val targetPeriod: String,
)