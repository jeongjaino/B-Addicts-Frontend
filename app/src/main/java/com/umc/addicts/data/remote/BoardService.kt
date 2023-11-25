package com.umc.addicts.data.remote

import com.umc.addicts.data.model.board.Board
import com.umc.addicts.data.model.board.BoardRequest
import com.umc.addicts.data.model.board.Comment
import com.umc.addicts.data.model.board.CommentRequest
import com.umc.addicts.data.model.board.DetailBoard
import com.umc.addicts.data.model.board.DetailReviewBoard
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.data.model.board.ReviewRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardService {

    @GET("/review-boards")
    suspend fun getReviewBoard(): List<ReviewBoard>

    @GET("/boards")
    suspend fun getBoard(): List<Board>

    @GET("/review-boards/{id}")
    suspend fun getReviewBoardDetail(
       @Path("id") id: Long
    ): DetailReviewBoard

    @GET("/boards/{id}")
    suspend fun getBoardDetail(
        @Path("id") id: Long
    ): DetailBoard

    @POST("/review-boards")
    suspend fun postReview(
        @Body reviewRequest: ReviewRequest
    )

    @POST("/boards")
    suspend fun postBoard(
        @Body boardRequest: BoardRequest
    )

    @POST("/comments")
    suspend fun postComment(
        @Body comment: CommentRequest
    )
}