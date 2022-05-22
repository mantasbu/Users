package com.kotlisoft.users.domain.repository

import com.kotlisoft.users.data.remote.CreateUserRequest
import com.kotlisoft.users.data.remote.CreatedUserResponse
import com.kotlisoft.users.data.remote.PaginatedUsersResponse
import com.kotlisoft.users.common.Resource
import com.kotlisoft.users.data.remote.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {

    suspend fun getUsersFromPage(page: Int): Flow<Resource<PaginatedUsersResponse>>

    suspend fun addUser(user: CreateUserRequest): Flow<Resource<UserResponse>>

    suspend fun deleteUser(id: Int): Flow<Resource<Response<Unit>>>
}