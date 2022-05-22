package com.kotlisoft.users.presentation.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kotlisoft.users.R
import com.kotlisoft.users.data.remote.UserResponse
import com.kotlisoft.users.domain.model.Gender
import com.kotlisoft.users.domain.model.Status

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserItem(
    user: UserResponse,
    onDeleteClicked: (UserResponse) -> Unit
) {
    var showConfirmationDialog by remember {
        mutableStateOf(false)
    }
    val statusColor = if (user.status == Status.ACTIVE) {
        Color.Green
    } else {
        Color.Red
    }
    val genderIcon = if (user.gender == Gender.MALE) {
        Icons.Rounded.Male
    } else {
        Icons.Rounded.Female
    }
    if (showConfirmationDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.delete_user))
            },
            text = {
               Text(text = stringResource(R.string.delete_user_confirmation))
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog = false
                        onDeleteClicked(user)
                    }
                ) {
                    Text(text = stringResource(R.string.delete))
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    onClick = {
                        showConfirmationDialog = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            onDismissRequest = { 
                showConfirmationDialog = false
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    showConfirmationDialog = true
                }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row {
                Icon(
                    imageVector = Icons.Rounded.Circle,
                    contentDescription = stringResource(R.string.status),
                    tint = statusColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = user.email,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column {
            Icon(
                imageVector = genderIcon,
                contentDescription = stringResource(R.string.gender)
            )
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}