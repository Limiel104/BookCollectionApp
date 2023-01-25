package com.example.bookcollectionapp.book_feature.domain.use_case

class ValidateFieldInputUseCase {
    operator fun invoke(fieldName: String, fieldInput: String): ValidationResult {
        if(fieldInput.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "$fieldName can't be blank"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)