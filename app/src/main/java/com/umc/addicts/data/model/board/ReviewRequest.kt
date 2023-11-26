package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest (
    val title: String,
    val startedAt: String,
    val endedAt: String,
    val before: String,
    val after: String,
    val keyword: String
)