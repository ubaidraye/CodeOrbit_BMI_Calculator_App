package com.raye.codeorbit_bmi_calculator_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.raye.codeorbit_bmi_calculator_app.presentation.bmi.BmiScreen
import com.raye.codeorbit_bmi_calculator_app.ui.theme.CodeOrbit_BMI_Calculator_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeOrbit_BMI_Calculator_AppTheme {
                BmiScreen()
            }
        }
    }
}
