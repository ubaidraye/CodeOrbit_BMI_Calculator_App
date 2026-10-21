package com.raye.codeorbit_bmi_calculator_app.domain.usecase

import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiCategory
import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiResult

class CalculateBmiUseCase {

    operator fun invoke(heightCm: Double, weightKg: Double): BmiResult {
        val heightMeters = heightCm / 100.0
        val bmiValue = weightKg / (heightMeters * heightMeters)

        val category = when {
            bmiValue < 18.5 -> BmiCategory.UNDERWEIGHT
            bmiValue < 25.0 -> BmiCategory.NORMAL
            bmiValue < 30.0 -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }

        return BmiResult(
            bmiValue = bmiValue,
            category = category
        )
    }
}
