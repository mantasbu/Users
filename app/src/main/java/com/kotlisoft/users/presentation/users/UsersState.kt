package com.kotlisoft.users.presentation.users

import com.kotlisoft.users.data.remote.UserResponse

data class UsersState(
    val users: List<UserResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorOccurred: Boolean = false
)