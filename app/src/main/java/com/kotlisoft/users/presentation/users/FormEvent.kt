package com.kotlisoft.users.presentation.users

sealed class FormEvent {
    data class NameChanged(val password: String) : FormEvent()
    data class EmailChanged(val email: String) : FormEvent()
    object Submit: FormEvent()
}