package com.kotlisoft.users.presentation.users

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.users.R

@Composable
fun UsersScreen(usersViewModel: UsersViewModel = hiltViewModel()) {
    var showAddUserDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showAddUserDialog) {
        Dialog(
            onDismissRequest = {
                showAddUserDialog = false
            }
        ) {
            AddUserDialogContent(
                onAddClicked = { user ->
                    showAddUserDialog = false
                    usersViewModel.addUser(user)
                },
                onCancelClicked = {
                    showAddUserDialog = false
                }
            )
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddUserDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_user)
                )
            }
        }
    ) {
        val state = usersViewModel.state
        val lastPage = usersViewModel.lastPage
        LaunchedEffect(key1 = lastPage) {
            usersViewModel.getUsersFromPage(page = lastPage)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
                if (state.errorOccurred) {
                    Snackbar {
                        Text(text = stringResource(R.string.load_users_failed))
                        Button(
                            onClick = {
                                usersViewModel.apply {
                                    unsetErrorOccurred()
                                    getUsersFromPage(lastPage)
                                }
                            }
                        ) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }
            }
            items(items = state.users) {
                UserItem(
                    user = it,
                    onDeleteClicked = { userToDelete ->
                        usersViewModel.deleteUserById(userToDelete.id)
                    }
                )
            }
        }
    }
}
