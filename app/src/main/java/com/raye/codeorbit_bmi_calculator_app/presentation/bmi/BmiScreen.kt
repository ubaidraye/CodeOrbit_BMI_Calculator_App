package com.raye.codeorbit_bmi_calculator_app.presentation.bmi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiCategory
import com.raye.codeorbit_bmi_calculator_app.domain.model.BmiResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiScreen(
    viewModel: BmiViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "BMI Calculator",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Enter your body details below",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = state.heightInput,
                onValueChange = { viewModel.onEvent(BmiUiEvent.OnHeightChanged(it)) },
                label = { Text("Height (cm)") },
                placeholder = { Text("e.g. 175") },
                isError = state.heightError != null,
                supportingText = {
                    state.heightError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.weightInput,
                onValueChange = { viewModel.onEvent(BmiUiEvent.OnWeightChanged(it)) },
                label = { Text("Weight (kg)") },
                placeholder = { Text("e.g. 70") },
                isError = state.weightError != null,
                supportingText = {
                    state.weightError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { viewModel.onEvent(BmiUiEvent.OnResetClicked) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text("Reset")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { viewModel.onEvent(BmiUiEvent.OnCalculateClicked) },
                    modifier = Modifier
                        .weight(2f)
                        .height(50.dp)
                ) {
                    Text("Calculate BMI", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(
                visible = state.bmiResult != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                state.bmiResult?.let { result ->
                    BmiResultCard(result = result)
                }
            }
        }
    }
}

@Composable
fun BmiResultCard(result: BmiResult) {
    val categoryColor = getCategoryColor(result.category)
    val categoryDescription = getCategoryDescription(result.category)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "YOUR RESULT",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "%.1f".format(result.bmiValue),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = result.category.name.replace("_", " "),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = categoryColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = categoryDescription,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun getCategoryColor(category: BmiCategory): Color {
    return when (category) {
        BmiCategory.UNDERWEIGHT -> Color(0xFF0288D1)
        BmiCategory.NORMAL -> Color(0xFF388E3C)
        BmiCategory.OVERWEIGHT -> Color(0xFFF57C00)
        BmiCategory.OBESE -> Color(0xFFD32F2F)
    }
}

private fun getCategoryDescription(category: BmiCategory): String {
    return when (category) {
        BmiCategory.UNDERWEIGHT -> "Your BMI indicates that you are underweight. Consider consulting a nutritionist."
        BmiCategory.NORMAL -> "Great job! You have a normal and healthy body weight."
        BmiCategory.OVERWEIGHT -> "Your BMI indicates that you are slightly overweight. Regular exercise can help."
        BmiCategory.OBESE -> "Your BMI indicates obesity. Prioritizing health and balanced nutrition is recommended."
    }
}
