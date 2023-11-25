package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class BoardRequest (
    val title: String,
    val content: String,
    val keyword: String,
)