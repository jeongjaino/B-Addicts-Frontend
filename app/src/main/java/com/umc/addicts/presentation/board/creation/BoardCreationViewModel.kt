package com.umc.addicts.presentation.board.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.board.BoardRequest
import com.umc.addicts.data.repository.board.BoardRepository
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardCreationViewModel @Inject constructor(
    private val repository: BoardRepository
): ViewModel() {

    private val _boardCreationEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val boardCreationEvent: SharedFlow<UiEvent<Unit>> = _boardCreationEvent.asSharedFlow()

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")
    private val keyword = MutableStateFlow("")

    fun setKeyword(keyword: String) {
        this.keyword.value = keyword
    }

    fun postBoard() {
        viewModelScope.launch {
            repository.postBoard(
                BoardRequest(
                    title = title.value,
                    content = content.value,
                    keyword = keyword.value
                )
            ).onSuccess {
                _boardCreationEvent.emit(UiEvent.Success(it))
            }.onFailure {
                _boardCreationEvent.emit(UiEvent.Failure(it))
            }
        }
    }

}