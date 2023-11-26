package com.umc.addicts.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.chat.ChatRoom
import com.umc.addicts.data.repository.chat.ChatRepository
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatCreationViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    private val _chatEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val chatEvent: SharedFlow<UiEvent<Unit>> = _chatEvent.asSharedFlow()

    val title: MutableStateFlow<String> = MutableStateFlow<String>("")
    val content: MutableStateFlow<String> = MutableStateFlow<String>("")
    val time: MutableStateFlow<String> = MutableStateFlow<String>("")
    val goal: MutableStateFlow<String> = MutableStateFlow<String>("")
    val targetPeriod: MutableStateFlow<String> = MutableStateFlow("")
    val peopleCount: MutableStateFlow<String> = MutableStateFlow<String>("")
    private val keyword: MutableStateFlow<String> = MutableStateFlow("")
    private val degree: MutableStateFlow<String> = MutableStateFlow("")

    fun setKeyword(keyword: String) {
        this.keyword.value = keyword
    }

    fun setDegree(degree: String) {
        this.degree.value = degree
    }

    fun postChat() {
        viewModelScope.launch {
            chatRepository.postChatRoom(
                ChatRoom(
                    title = title.value,
                    deadline = time.value,
                    introduction = content.value,
                    memberNum = peopleCount.value.toInt(),
                    missions = listOf(goal.value),
                    degree = degree.value,
                    keyword = keyword.value,
                    targetPeriod = targetPeriod.value
                )
            ).onFailure {
                _chatEvent.emit(UiEvent.Failure(it))
                Log.e("viewMOdel", it.message.toString())
            }.onSuccess {
                _chatEvent.emit(UiEvent.Success(it))
            }
        }
    }
}