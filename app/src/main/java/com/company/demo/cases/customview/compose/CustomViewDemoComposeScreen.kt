package com.company.demo.cases.customview.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.demo.cases.customview.mvi.CustomViewDemoState
import com.company.demo.cases.customview.mvi.CustomViewDemoUiState

/**
 * 宣告式的可重複使用 Composable 元件 (Reusable Composable Function)
 * 這與 XML 繁瑣的自訂 View 設計相比，Compose 的重用僅需宣告一個帶有狀態參數與 Event Callback 的函數即可。
 * 所有繪製與狀態管理皆在此自包含 (Self-contained) 與極度輕量化。
 */
@Composable
fun StatefulLoadingButton(
    state: CustomViewDemoState,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = state == CustomViewDemoState.Idle,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (state) {
                is CustomViewDemoState.Loading -> {
                    CircularProgressIndicator(
                        color = androidx.compose.material3.LocalContentColor.current,
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                is CustomViewDemoState.Success -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                is CustomViewDemoState.Idle -> {
                    // 不需要額外圖示
                }
            }
            Text(text = text)
        }
    }
}

@Composable
fun CustomViewDemoComposeScreen(
    uiState: CustomViewDemoUiState,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // 呼叫可重用的 StatefulLoadingButton 可重用元件
        StatefulLoadingButton(
            state = uiState.state,
            text = uiState.text,
            onClick = onClick
        )
    }
}
