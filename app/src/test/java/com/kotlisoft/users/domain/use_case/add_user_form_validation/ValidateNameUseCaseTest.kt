package com.kotlisoft.users.domain.use_case.add_user_form_validation

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ValidateNameUseCaseTest {

    private lateinit var validateName: ValidateNameUseCase

    @Before
    fun setUp() {
        validateName = ValidateNameUseCase()
    }

    @Test
    fun validateNameWhenNameIsValidReturnsTrue() {
        val name = "Max Miler"
        val result = validateName(name)
        assertTrue(result.successful)
    }

    @Test
    fun validateNameWhenNameIsEmptyReturnsFalse() {
        val name = ""
        val result = validateName(name)
        assertFalse(result.successful)
    }
}