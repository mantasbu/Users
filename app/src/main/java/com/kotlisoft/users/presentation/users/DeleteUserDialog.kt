package com.kotlisoft.users.presentation.users

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kotlisoft.users.R

@Composable
fun DeleteUserDialog(
    onConfirmButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
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
                    onConfirmButtonClicked()
                }
            ) {
                Text(text = stringResource(R.string.delete))
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}