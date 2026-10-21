package com.raye.codeorbit_bmi_calculator_app.domain.usecase

import com.raye.codeorbit_bmi_calculator_app.domain.model.ValidationError

data class ValidationResult(
    val heightError: ValidationError? = null,
    val weightError: ValidationError? = null
) {
    val isValid: Boolean
        get() = heightError == null && weightError == null
}

class ValidateInputUseCase {

    operator fun invoke(
        heightInput: String,
        weightInput: String
    ): ValidationResult {
        val trimmedHeight = heightInput.trim()
        val trimmedWeight = weightInput.trim()

        val heightError = when {
            trimmedHeight.isEmpty() -> ValidationError.EMPTY_HEIGHT
            trimmedHeight.toDoubleOrNull() == null -> ValidationError.INVALID_HEIGHT
            trimmedHeight.toDouble() !in 30.0..300.0 -> ValidationError.OUT_OF_RANGE_HEIGHT
            else -> null
        }

        val weightError = when {
            trimmedWeight.isEmpty() -> ValidationError.EMPTY_WEIGHT
            trimmedWeight.toDoubleOrNull() == null -> ValidationError.INVALID_WEIGHT
            trimmedWeight.toDouble() !in 2.0..500.0 -> ValidationError.OUT_OF_RANGE_WEIGHT
            else -> null
        }

        return ValidationResult(
            heightError = heightError,
            weightError = weightError
        )
    }
}
