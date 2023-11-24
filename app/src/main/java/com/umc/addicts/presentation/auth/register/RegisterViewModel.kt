package com.umc.addicts.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.auth.RegisterRequest
import com.umc.addicts.data.repository.AuthRepository
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _registerEvent : MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val registerEvent: SharedFlow<UiEvent<Unit>> = _registerEvent.asSharedFlow()

    private val id: MutableStateFlow<String> = MutableStateFlow<String>("")
    private val password: MutableStateFlow<String> = MutableStateFlow<String>("")
    private val _imageByteArray: MutableStateFlow<ByteArray> = MutableStateFlow(ByteArray(0))
    val nickname: MutableStateFlow<String> = MutableStateFlow("")

    fun setImageByteArray(imageByteArray: ByteArray) {
        _imageByteArray.value = imageByteArray
    }

    fun setIdAndPassword(id: String, password: String) {
        this.id.value = id
        this.password.value = password
    }

    fun registerUser() {
        viewModelScope.launch {
            repository.register(
                registerRequest = RegisterRequest(
                    id = id.value,
                    password = password.value,
                    nickname = nickname.value,
                    profileImage = _imageByteArray.value
                )
            )
        }
    }
}