package com.umc.addicts.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel: ViewModel() {

    private val _loginEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val loginEvent: SharedFlow<UiEvent<Unit>> = _loginEvent.asSharedFlow()


    val id: MutableStateFlow<String> = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = MutableStateFlow<String>("")

    fun login() {
        viewModelScope.launch {

        }
    }
}