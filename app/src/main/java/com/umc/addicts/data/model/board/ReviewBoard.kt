package com.umc.addicts.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewBoard (
    @SerialName("id") val boardId: Long,
    val nickname: String,
    val title: String,
    val keyword: String,
    val createdAt: String,
)