package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class DetailReviewBoard (
    val nickname: String,
    val title: String,
    val endedAt: String,
    val startedAt: String,
    val before: String,
    val after: String,
    val keyword: String,
    val createdAt: String,
    val good: Int
)