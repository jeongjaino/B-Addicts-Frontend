package com.umc.addicts.presentation.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.addicts.data.model.board.Board
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.data.repository.board.BoardRepository
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
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
): ViewModel() {

    private val _boardEvent: MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val boardEvent: SharedFlow<UiEvent<Unit>> = _boardEvent.asSharedFlow()

    private val _boardUiState: MutableStateFlow<BoardUiState> = MutableStateFlow(BoardUiState.Init)
    val boardUiState: StateFlow<BoardUiState> = _boardUiState.asStateFlow()

    init {
        getBoard()
    }
    fun getBoard() {
        viewModelScope.launch {
            repository.getBoard()
                .onSuccess {
                    _boardUiState.value = BoardUiState.BoardState(it)
                }
                .onFailure {
                    _boardEvent.emit(UiEvent.Failure(it))
                }
        }
    }

    fun getReviewBoard() {
        viewModelScope.launch {
            repository.getReviewBoard()
                .onSuccess {
                    _boardUiState.value = BoardUiState.ReviewBoardState(it)
                }
                .onFailure {
                    _boardEvent.emit(UiEvent.Failure(it))
                }
        }
    }

    sealed class BoardUiState {
        data object Init: BoardUiState()

        data class BoardState(val board: List<Board>): BoardUiState()

        data class ReviewBoardState(val reviewBoard: List<ReviewBoard>): BoardUiState()
    }
}