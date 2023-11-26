package com.umc.addicts.data.repository.auth

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

const val FILE_NAME = "CUT_TOXICS_DATA"

class TokenRepositoryImpl @Inject constructor(
    context: Context
) : TokenRepository{
    private val tokenStore by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    override var accessToken: String
        set(value) = tokenStore.edit { putString("ACCESS_TOKEN", value) }
        get() = tokenStore.getString("ACCESS_TOKEN", "") ?: ""

    override fun clearToken() {
        tokenStore.edit { clear() }
    }
}