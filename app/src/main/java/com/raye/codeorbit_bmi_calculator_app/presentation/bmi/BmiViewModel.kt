package com.raye.codeorbit_bmi_calculator_app.presentation.bmi

import androidx.lifecycle.ViewModel
import com.raye.codeorbit_bmi_calculator_app.domain.model.ValidationError
import com.raye.codeorbit_bmi_calculator_app.domain.usecase.CalculateBmiUseCase
import com.raye.codeorbit_bmi_calculator_app.domain.usecase.ValidateInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BmiViewModel(
    private val validateInputUseCase: ValidateInputUseCase = ValidateInputUseCase(),
    private val calculateBmiUseCase: CalculateBmiUseCase = CalculateBmiUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BmiUiState())
    val uiState: StateFlow<BmiUiState> = _uiState.asStateFlow()

    fun onEvent(event: BmiUiEvent) {
        when (event) {
            is BmiUiEvent.OnHeightChanged -> {
                _uiState.update { it.copy(heightInput = event.height, heightError = null) }
            }
            is BmiUiEvent.OnWeightChanged -> {
                _uiState.update { it.copy(weightInput = event.weight, weightError = null) }
            }
            is BmiUiEvent.OnCalculateClicked -> {
                calculateBmi()
            }
            is BmiUiEvent.OnResetClicked -> {
                _uiState.update { BmiUiState() }
            }
        }
    }

    private fun calculateBmi() {
        val currentState = _uiState.value
        val validationResult = validateInputUseCase(currentState.heightInput, currentState.weightInput)

        if (!validationResult.isValid) {
            _uiState.update {
                it.copy(
                    heightError = validationResult.heightError?.toErrorMessage(),
                    weightError = validationResult.weightError?.toErrorMessage(),
                    bmiResult = null
                )
            }
            return
        }

        val heightCm = currentState.heightInput.trim().toDouble()
        val weightKg = currentState.weightInput.trim().toDouble()
        val result = calculateBmiUseCase(heightCm, weightKg)

        _uiState.update {
            it.copy(
                heightError = null,
                weightError = null,
                bmiResult = result
            )
        }
    }

    private fun ValidationError.toErrorMessage(): String {
        return when (this) {
            ValidationError.EMPTY_HEIGHT -> "Height cannot be empty"
            ValidationError.EMPTY_WEIGHT -> "Weight cannot be empty"
            ValidationError.INVALID_HEIGHT -> "Enter a valid height number"
            ValidationError.INVALID_WEIGHT -> "Enter a valid weight number"
            ValidationError.OUT_OF_RANGE_HEIGHT -> "Height must be between 30 and 300 cm"
            ValidationError.OUT_OF_RANGE_WEIGHT -> "Weight must be between 2 and 500 kg"
        }
    }
}
