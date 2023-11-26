package com.umc.addicts.presentation.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.auth.RegisterRequest
import com.umc.addicts.data.model.auth.Toxic
import com.umc.addicts.data.repository.auth.AuthRepository
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val nickname: MutableStateFlow<String> = MutableStateFlow("")
    private val toxicList: MutableStateFlow<List<Toxic>> = MutableStateFlow(emptyList())

    fun setImageByteArray(imageByteArray: ByteArray) {
        _imageByteArray.value = imageByteArray
    }

    fun setInfo(nickname: String, id: String, password: String) {
        this.id.value = id
        this.password.value = password
        this.nickname.value = nickname
    }

    fun setToxicList(toxicList: List<Toxic>) {
        this.toxicList.value = toxicList
    }

    fun registerProfile() {
        viewModelScope.launch {
            repository.registerProfile(
                imageByteArray = _imageByteArray.value
            ).onSuccess {
                registerUser(it.address)
            }.onFailure {
                _registerEvent.emit(UiEvent.Failure(it))
            }
        }
    }

    private fun registerUser(imageUrl: String) {
        viewModelScope.launch {
            repository.register(
                registerRequest = RegisterRequest(
                    userId = id.value,
                    password = password.value,
                    nickname = nickname.value,
                    image = imageUrl,
                    toxics = toxicList.value,
                )
            ).onFailure {
                Log.e("tag",it.message.toString())
            }.onSuccess {
                _registerEvent.emit(UiEvent.Success(Unit))
            }
        }
    }
}