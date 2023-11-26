package com.umc.addicts.presentation.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTimeString() : String {
    val localDateTime =  LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
}