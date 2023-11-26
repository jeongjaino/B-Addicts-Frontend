package com.umc.addicts.data.repository.board

import com.umc.addicts.data.model.board.Board
import com.umc.addicts.data.model.board.BoardRequest
import com.umc.addicts.data.model.board.Comment
import com.umc.addicts.data.model.board.CommentRequest
import com.umc.addicts.data.model.board.DetailBoard
import com.umc.addicts.data.model.board.DetailReviewBoard
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.data.model.board.ReviewRequest

interface BoardRepository {
    suspend fun getReviewBoard(): Result<List<ReviewBoard>>

    suspend fun getBoard(): Result<List<Board>>

    suspend fun getBoardById(id: Long): Result<DetailBoard>

    suspend fun getReviewBoardById(id: Long): Result<DetailReviewBoard>

    suspend fun postReview(reviewRequest: ReviewRequest): Result<Unit>

    suspend fun postBoard(boardRequest: BoardRequest): Result<Unit>

    suspend fun postComment(comment: CommentRequest): Result<Unit>
}