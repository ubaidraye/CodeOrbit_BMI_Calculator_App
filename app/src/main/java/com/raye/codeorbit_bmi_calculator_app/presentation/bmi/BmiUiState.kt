package com.raye.codeorbit_bmi_calculator_app.presentation.bmi

import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiResult

data class BmiUiState(
    val heightInput: String = "",
    val weightInput: String = "",
    val heightError: String? = null,
    val weightError: String? = null,
    val bmiResult: BmiResult? = null
)
