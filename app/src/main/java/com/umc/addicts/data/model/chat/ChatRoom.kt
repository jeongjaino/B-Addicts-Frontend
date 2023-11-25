package com.umc.addicts.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoom (
    val title: String,
    val deadline: String,
    val targetPeriod: String,
    val introduction: String,
    val keyword: String,
    val memberNum: Int,
    val missions: List<String>,
    val degree: String
)