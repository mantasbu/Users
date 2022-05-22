package com.kotlisoft.users.domain.use_case.add_user_form_validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
