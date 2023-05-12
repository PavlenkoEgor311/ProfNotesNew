package com.example.profnotes.data.network.Api

import com.example.profnotes.data.models.*
import com.example.profnotes.model.request.LoginResponse
import com.example.profnotes.model.request.UserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RegisterApi {
    @GET("test")
    suspend fun test(): Response<ResponseBody>

    @POST("signup")
    suspend fun signIn(
        @Body user: SignInUser
    ): Response<ResponseBody>

    @POST("signin")
    suspend fun logIn(
        @Body user: UserRequest
    ): LoginResponse

    @POST("updateUser")
    suspend fun updateUser(
        @Body user: UpdateUserRequest
    )

    @POST("findUser")
    suspend fun findFriends(
        @Body searchUser: FindUserRequest
    ): List<UserFindRequest>?

    @GET("getUserById")
    suspend fun getData(
        @Query("id") id: Long
    ): User

    @POST("getListFriends")
    suspend fun getListFriends(
        @Body listId: List<UserFriendId>
    ): List<UserFindRequest>?

    @PATCH("addFriend")
    suspend fun addFriend(
        @Body update: UpdateUserFriendsRequest
    )

    @POST("delFriend")
    suspend fun deleteFriend(
        @Body update: UpdateUserFriendsRequest
    )
}