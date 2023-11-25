package com.umc.addicts.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class DetailChatRoom (
    val title: String,
    val memberNum: Int,
    val introduction: String,
    val keyword: String,
    val degree: String,
    val deadline: String,
    val targetPeriod: String,
    val missions: List<String>
)