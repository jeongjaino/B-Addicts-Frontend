package com.umc.addicts.presentation.board.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.board.Comment
import com.umc.addicts.data.model.board.CommentRequest
import com.umc.addicts.data.model.board.DetailBoard
import com.umc.addicts.data.repository.board.BoardRepository
import com.umc.addicts.presentation.utils.UiEvent
import com.umc.addicts.presentation.utils.toLocalDateTimeString
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
class BoardDetailViewModel @Inject constructor(
    private val repository: BoardRepository
): ViewModel() {

    private val _boardDetailEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val boardDetailEvent: SharedFlow<UiEvent<Unit>> = _boardDetailEvent.asSharedFlow()

    private val _boardDetail: MutableStateFlow<DetailBoard> = MutableStateFlow(DetailBoard())
    val boardDetail : StateFlow<DetailBoard> = _boardDetail.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comment>> = _commentList.asStateFlow()

    private val _id : MutableStateFlow<Long> = MutableStateFlow(0L)

    val comment: MutableStateFlow<String> = MutableStateFlow("")

    fun getBoardById(id: Long) {
        viewModelScope.launch {
            repository.getBoardById(id)
                .onSuccess {
                    _boardDetail.value = it.copy(
                        createdAt = it.createdAt.toLocalDateTimeString()
                    )
                    _commentList.value = it.commentList
                    _id.value = id
                }
                .onFailure {
                    _boardDetailEvent.emit(UiEvent.Failure(it))
                }
        }
    }

    fun postComment() {
        if(comment.value.isNotBlank()) {
            viewModelScope.launch {
                repository.postComment(
                    CommentRequest(
                        _id.value,
                        comment.value
                    )
                ).onSuccess {
                    _boardDetailEvent.emit(UiEvent.Success(it))
                    getBoardById(_id.value)
                }.onFailure {
                    _boardDetailEvent.emit(UiEvent.Failure(it))
                }
            }
        }
    }
}