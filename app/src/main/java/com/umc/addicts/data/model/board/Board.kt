package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class Board (
    val boardId: Long,
    val nickname: String,
    val title: String,
    val keyword: String,
    val createdAt: String,
    val view: Int,
    val commentCount: Int
)
