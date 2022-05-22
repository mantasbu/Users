package com.kotlisoft.users.presentation.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlisoft.users.data.remote.CreateUserRequest
import com.kotlisoft.users.domain.repository.UserRepository
import com.kotlisoft.users.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(UsersState())
    var lastPage by mutableStateOf(1)

    fun unsetErrorOccurred() {
        state = state.copy(errorOccurred = false)
    }

    fun getUsersFromPage(page: Int) = viewModelScope.launch {
        userRepository.getUsersFromPage(page)
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let { response ->
                            val totalPages = response.meta.pagination.pages
                            if (totalPages != lastPage) {
                                lastPage = totalPages
                            } else {
                                state = state.copy(
                                    users = response.users
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorOccurred = true
                        )
                    }
                }
            }
    }

    fun addUser(user: CreateUserRequest) = viewModelScope.launch {
        userRepository.addUser(user)
            .collect { createdUserResponse ->
                when (createdUserResponse) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = createdUserResponse.isLoading)
                    }
                    is Resource.Success -> {
                        val currentUsers = state.users.toMutableList()
                        if (createdUserResponse.data != null) {
                            currentUsers.add(createdUserResponse.data)
                            state = state.copy(users = currentUsers)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorOccurred = true
                        )
                    }
                }
            }
    }

    fun deleteUserById(id: Int) = viewModelScope.launch {
        userRepository.deleteUser(id)
            .collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = response.isLoading)
                    }
                    is Resource.Success -> {
                        val currentUsers = state.users.toMutableList()
                        val userToDelete = currentUsers.first {
                            it.id == id
                        }
                        currentUsers.remove(userToDelete)
                        state = state.copy(users = currentUsers)
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorOccurred = true
                        )
                    }
                }
            }
    }
}