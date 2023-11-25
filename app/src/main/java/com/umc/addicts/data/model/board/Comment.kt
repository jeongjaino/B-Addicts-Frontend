package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class Comment (
    val nickname: String,
    val content: String,
    val createdAt: String
)