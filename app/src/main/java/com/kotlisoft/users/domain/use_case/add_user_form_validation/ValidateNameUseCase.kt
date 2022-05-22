package com.kotlisoft.users.domain.use_case.add_user_form_validation

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}