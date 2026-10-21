package com.raye.codeorbit_bmi_calculator_app.presentation.bmi

sealed interface BmiUiEvent {
    data class OnHeightChanged(val height: String) : BmiUiEvent
    data class OnWeightChanged(val weight: String) : BmiUiEvent
    data object OnCalculateClicked : BmiUiEvent
    data object OnResetClicked : BmiUiEvent
}
