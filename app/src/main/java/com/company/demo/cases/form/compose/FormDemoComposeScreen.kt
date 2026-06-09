package com.company.demo.cases.form.compose

import com.company.demo.cases.form.mvi.FormDemoUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    onAddField: () -> Unit,
    onRemoveField: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 使用 repeat 遍歷欄位列表，動態渲染每一個輸入元件
        // 在 Compose 中，新增或減少欄位只需改變狀態 fields 數量即可，UI 會自動並高效地聲明式重繪
        repeat(uiState.fields.size) { index ->
            val field = uiState.fields[index]
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

        // 動態增減欄位的控制鈕
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onAddField,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "新增欄位")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onRemoveField,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "移除欄位")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

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
