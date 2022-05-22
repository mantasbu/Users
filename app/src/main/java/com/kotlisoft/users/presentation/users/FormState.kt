package com.kotlisoft.users.presentation.users

data class FormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null
)