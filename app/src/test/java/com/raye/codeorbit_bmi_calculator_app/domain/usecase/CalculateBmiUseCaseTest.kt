package com.raye.codeorbit_bmi_calculator_app.domain.usecase

import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiCategory
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateBmiUseCaseTest {

    private val calculateBmiUseCase = CalculateBmiUseCase()

    @Test
    fun `calculate BMI for normal weight returns correct category`() {
        val result = calculateBmiUseCase(heightCm = 175.0, weightKg = 70.0)
        assertEquals(22.857, result.bmiValue, 0.01)
        assertEquals(BmiCategory.NORMAL, result.category)
    }

    @Test
    fun `calculate BMI for underweight returns UNDERWEIGHT category`() {
        val result = calculateBmiUseCase(heightCm = 180.0, weightKg = 50.0)
        assertEquals(BmiCategory.UNDERWEIGHT, result.category)
    }

    @Test
    fun `calculate BMI for overweight returns OVERWEIGHT category`() {
        val result = calculateBmiUseCase(heightCm = 170.0, weightKg = 80.0)
        assertEquals(BmiCategory.OVERWEIGHT, result.category)
    }

    @Test
    fun `calculate BMI for obese returns OBESE category`() {
        val result = calculateBmiUseCase(heightCm = 160.0, weightKg = 90.0)
        assertEquals(BmiCategory.OBESE, result.category)
    }
}
