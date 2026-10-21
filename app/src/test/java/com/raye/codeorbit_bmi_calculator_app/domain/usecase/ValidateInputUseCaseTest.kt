package com.raye.codeorbit_bmi_calculator_app.domain.usecase

import com.raye.codeorbit_bmi_calculator_app.domain.model.ValidationError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateInputUseCaseTest {

    private val validateInputUseCase = ValidateInputUseCase()

    @Test
    fun `valid inputs returns isValid true and no errors`() {
        val result = validateInputUseCase("175", "70")
        assertTrue(result.isValid)
        assertNull(result.heightError)
        assertNull(result.weightError)
    }

    @Test
    fun `empty inputs returns empty validation errors`() {
        val result = validateInputUseCase("", "")
        assertFalse(result.isValid)
        assertEquals(ValidationError.EMPTY_HEIGHT, result.heightError)
        assertEquals(ValidationError.EMPTY_WEIGHT, result.weightError)
    }

    @Test
    fun `out of range inputs returns out of range errors`() {
        val result = validateInputUseCase("10", "1000")
        assertFalse(result.isValid)
        assertEquals(ValidationError.OUT_OF_RANGE_HEIGHT, result.heightError)
        assertEquals(ValidationError.OUT_OF_RANGE_WEIGHT, result.weightError)
    }
}
