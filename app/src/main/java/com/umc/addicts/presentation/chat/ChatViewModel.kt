package com.umc.addicts.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.chat.ChatRoomResponse
import com.umc.addicts.data.repository.chat.ChatRepository
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
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    private val _chatEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val chatEvent: SharedFlow<UiEvent<Unit>> = _chatEvent.asSharedFlow()

    private val _chatRoomList: MutableStateFlow<List<ChatRoomResponse>> = MutableStateFlow(
        emptyList()
    )
    val chatRoomList: StateFlow<List<ChatRoomResponse>> = _chatRoomList.asStateFlow()

    init{
        getChatList()
    }

    private fun getChatList() {
        viewModelScope.launch {
            chatRepository.getChatRoomList()
                .onSuccess {
                    _chatRoomList.value = it
                }
                .onFailure {
                    _chatEvent.emit(UiEvent.Failure(it))
                }
        }
    }
}