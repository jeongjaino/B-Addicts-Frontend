package com.umc.addicts.data.repository.board

import com.umc.addicts.data.model.board.Board
import com.umc.addicts.data.model.board.BoardRequest
import com.umc.addicts.data.model.board.Comment
import com.umc.addicts.data.model.board.CommentRequest
import com.umc.addicts.data.model.board.DetailBoard
import com.umc.addicts.data.model.board.DetailReviewBoard
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.data.model.board.ReviewRequest
import com.umc.addicts.data.remote.BoardService
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    val service: BoardService
): BoardRepository{
    override suspend fun getReviewBoard(): Result<List<ReviewBoard>> {
        return runCatching {
            service.getReviewBoard()
        }
    }

    override suspend fun getBoard(): Result<List<Board>> {
        return runCatching {
            service.getBoard()
        }
    }

    override suspend fun postReview(reviewRequest: ReviewRequest): Result<Unit> {
        return runCatching {
            service.postReview(reviewRequest)
        }
    }

    override suspend fun postBoard(boardRequest: BoardRequest): Result<Unit> {
        return runCatching {
            service.postBoard(boardRequest)
        }
    }

    override suspend fun postComment(comment: CommentRequest): Result<Unit> {
        return runCatching {
            service.postComment(comment)
        }
    }

    override suspend fun getBoardById(id: Long): Result<DetailBoard> {
        return runCatching {
            service.getBoardDetail(id)
        }
    }

    override suspend fun getReviewBoardById(id: Long): Result<DetailReviewBoard> {
        return runCatching {
            service.getReviewBoardDetail(id)
        }
    }
}