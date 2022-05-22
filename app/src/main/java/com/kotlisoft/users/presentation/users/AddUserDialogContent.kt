package com.kotlisoft.users.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.users.R
import com.kotlisoft.users.data.remote.CreateUserRequest
import com.kotlisoft.users.domain.model.Gender
import com.kotlisoft.users.domain.model.Status

@Composable
fun AddUserDialogContent(
    validationViewModel: ValidationViewModel = hiltViewModel(),
    onAddClicked: (CreateUserRequest) -> Unit,
    onCancelClicked: () -> Unit
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    val state = validationViewModel.state
    LaunchedEffect(Unit) {
        validationViewModel.validationEvents.collect { event ->
            when (event) {
                ValidationViewModel.ValidationEvent.Success -> {
                    val user = CreateUserRequest(
                        name = name,
                        email = email,
                        gender = Gender.MALE,
                        status = Status.ACTIVE
                    )
                    onAddClicked(user)
                    validationViewModel.clearFormFields()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = R.string.add_user))
            Icon(
                imageVector = Icons.Outlined.Cancel,
                contentDescription = stringResource(id = R.string.cancel),
                modifier = Modifier.clickable {
                    onCancelClicked()
                }
            )
        }
        Row {
            OutlinedTextField(
                value = name,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.name))
                },
                onValueChange = {
                    name = it
                    validationViewModel.onEvent(FormEvent.NameChanged(name))
                }
            )
        }
        if (state.nameError != null) {
            Text(
                text = state.nameError,
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row {
            OutlinedTextField(
                value = email,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                onValueChange = {
                    email = it
                    validationViewModel.onEvent(FormEvent.EmailChanged(email))
                }
            )
        }
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    validationViewModel.onEvent(event = FormEvent.Submit)
                }
            ) {
                Text(text = stringResource(id = R.string.add_user))
            }
        }
    }
}