package com.raye.codeorbit_bmi_calculator_app.domain.model

enum class ValidationError {
    EMPTY_HEIGHT,
    EMPTY_WEIGHT,
    INVALID_HEIGHT,
    INVALID_WEIGHT,
    OUT_OF_RANGE_HEIGHT,
    OUT_OF_RANGE_WEIGHT
}