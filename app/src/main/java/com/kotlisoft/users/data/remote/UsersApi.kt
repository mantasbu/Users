package com.kotlisoft.users.data.remote

import com.kotlisoft.users.common.Constants.ACCESS_TOKEN
import retrofit2.Response
import retrofit2.http.*

interface UsersApi {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): PaginatedUsersResponse

    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @POST("users")
    suspend fun addUser(
        @Body user: CreateUserRequest
    ): CreatedUserResponse


    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @DELETE("users/{userId}")
    suspend fun removeUser(
        @Path("userId") userId: Int
    ): Response<Unit>
}