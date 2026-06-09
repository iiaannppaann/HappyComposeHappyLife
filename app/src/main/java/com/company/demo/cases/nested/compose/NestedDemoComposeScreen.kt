package com.company.demo.cases.nested.compose

import com.company.demo.cases.nested.mvi.NestedDemoUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NestedDemoComposeScreen(uiState: NestedDemoUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 利用縮排展示巢狀層級，不使用任何文字顏色與樣式設定
        Text(text = uiState.department, modifier = Modifier.padding(start = 0.dp, bottom = 8.dp))
        Text(text = uiState.team, modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))
        Text(text = uiState.group, modifier = Modifier.padding(start = 32.dp, bottom = 8.dp))
        Text(text = uiState.role, modifier = Modifier.padding(start = 48.dp, bottom = 8.dp))
        Text(text = uiState.name, modifier = Modifier.padding(start = 64.dp, bottom = 8.dp))
    }
}
