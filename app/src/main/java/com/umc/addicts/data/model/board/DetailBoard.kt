package com.umc.addicts.data.model.board

import kotlinx.serialization.Serializable

@Serializable
data class DetailBoard (
    val nickname: String,
    val title: String,
    val content: String,
    val view: Int,
    val createdAt: String,
    val commentList: List<Comment>
) {
    constructor(): this(
        "",
        "",
        "",
        0,
        "",
        emptyList()
    )
}