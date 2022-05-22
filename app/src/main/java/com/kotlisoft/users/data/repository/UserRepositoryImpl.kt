package com.kotlisoft.users.data.repository

import com.kotlisoft.users.data.remote.*
import com.kotlisoft.users.domain.repository.UserRepository
import com.kotlisoft.users.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UsersApi
) : UserRepository {

    override suspend fun getUsersFromPage(page: Int): Flow<Resource<PaginatedUsersResponse>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                api.getUsers(page = page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load users"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load users"))
                null
            }
            response.let { paginatedUsersResponse ->
                emit(Resource.Loading(isLoading = false))
                emit(Resource.Success(data = paginatedUsersResponse))
            }
        }
    }

    override suspend fun addUser(user: CreateUserRequest): Flow<Resource<UserResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val createdUserResponse = api.addUser(user)
                if (createdUserResponse.code == 201) {
                    val userResponse = createdUserResponse.data
                    emit(Resource.Loading(isLoading = false))
                    emit(Resource.Success(data = userResponse))
                } else {
                    emit(Resource.Error(message = "User was not added!"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add a user"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add a user"))
            }
        }
    }

    override suspend fun deleteUser(id: Int): Flow<Resource<Response<Unit>>> {
        return flow {
            emit(Resource.Loading(true))
            val response = api.removeUser(id)
            if (response.isSuccessful) {
                emit(Resource.Loading(isLoading = false))
                emit(Resource.Success())
            } else {
                emit(Resource.Error(message = response.message()))
            }
        }
    }
}