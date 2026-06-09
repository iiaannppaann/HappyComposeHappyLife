package com.company.demo.cases.form.compose

import com.company.demo.cases.form.mvi.FormDemoUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormDemoComposeScreen(
    uiState: FormDemoUiState,
    onValueChange: (String, String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        uiState.fields.forEach { field ->
            Text(text = field.label)
            OutlinedTextField(
                value = field.value,
                onValueChange = { onValueChange(field.id, it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "送出")
        }

        if (uiState.submitResult.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = uiState.submitResult)
        }
    }
}
