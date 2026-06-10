package com.company.demo.cases.interop.compose

import android.widget.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.company.demo.cases.interop.mvi.InteropUiState

@Composable
fun InteropComposeScreen(
    uiState: InteropUiState,
    onIncrement: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "這是 Compose 畫面元件")
            Text(text = "當前計數值: ${uiState.count}")

            Spacer(modifier = Modifier.height(16.dp))

            // 【混合互操作】：在 Compose 樹中直接嵌套 Legacy Android Button
            // 點擊此 Legacy 視圖將同步觸發 Compose 的 MVI 狀態變更
            AndroidView(
                factory = { context ->
                    Button(context).apply {
                        text = "點我 (來自 XML/View 視圖元件)"
                        setOnClickListener {
                            onIncrement()
                        }
                    }
                },
                update = { button ->
                    // 每次重組時，同步狀態數值到 Legacy 視圖上
                    button.text = "點我 (來自 XML/View 視圖元件): ${uiState.count}"
                }
            )
        }
    }
}
