package com.example.bookcollectionapp.book_feature.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateFieldInputUseCaseTest {

    private lateinit var validateFiledInputUseCase: ValidateFieldInputUseCase

    @Before
    fun setUp() {
        validateFiledInputUseCase = ValidateFieldInputUseCase()
    }

    @Test
    fun fieldInput_isBlank_returnError() {
        val result = validateFiledInputUseCase("Genre","")

        assertThat(result.successful).isFalse()
    }

    @Test
    fun fieldInput_isNotBlank_returnSuccess() {
        val result = validateFiledInputUseCase("Genre","field input")

        assertThat(result.successful).isTrue()
    }
}