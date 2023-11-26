package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest (
    val boardId: Long,
    val content: String,
)