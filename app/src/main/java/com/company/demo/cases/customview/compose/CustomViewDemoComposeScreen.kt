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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

// ==========================================
// 預覽區域 (Previews) - 展現 Compose 強大的即時預覽能力
// 傳統 XML Custom View 很難輕易且完美地預覽其動態狀態變化
// ==========================================

@Preview(showBackground = true, name = "1. Idle State")
@Composable
fun PreviewStatefulLoadingButton_Idle() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            StatefulLoadingButton(
                state = CustomViewDemoState.Idle,
                text = "提交資料",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "2. Loading State")
@Composable
fun PreviewStatefulLoadingButton_Loading() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            StatefulLoadingButton(
                state = CustomViewDemoState.Loading,
                text = "處理中...",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "3. Success State")
@Composable
fun PreviewStatefulLoadingButton_Success() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            StatefulLoadingButton(
                state = CustomViewDemoState.Success,
                text = "提交成功",
                onClick = {}
            )
        }
    }
}
