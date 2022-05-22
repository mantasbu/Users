package com.kotlisoft.users.presentation.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlisoft.users.domain.use_case.add_user_form_validation.ValidateEmailUseCase
import com.kotlisoft.users.domain.use_case.add_user_form_validation.ValidateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    var state by mutableStateOf(FormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: FormEvent) {
        when(event) {
            is FormEvent.NameChanged -> {
                state = state.copy(name = event.password)
            }
            is FormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is FormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun clearFormFields() {
        onEvent(FormEvent.NameChanged(""))
        onEvent(FormEvent.EmailChanged(""))
    }

    private fun submitData() {
        val nameResult = validateNameUseCase(state.name)
        val emailResult = validateEmailUseCase(state.email)

        val hasError = listOf(
            nameResult,
            emailResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}