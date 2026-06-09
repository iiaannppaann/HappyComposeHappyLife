package com.company.demo.cases.multitype.compose

import com.company.demo.cases.multitype.mvi.MultiTypeUiState
import com.company.demo.cases.multitype.mvi.PlainTextItem
import com.company.demo.cases.multitype.mvi.ColorTextItem
import com.company.demo.cases.multitype.mvi.ItalicTextItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun MultiTypeComposeScreen(uiState: MultiTypeUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 在 Compose 中，多種 ViewType 的清單完全不需要 Adapter Boilerplate 與 ViewHolder 樣板！
        // 我們只需要在 items 內部直接使用 Kotlin 的強大 pattern matching (when) 來宣告各自對應的 UI 元件。
        items(uiState.items) { item ->
            when (item) {
                is PlainTextItem -> {
                    Text(
                        text = item.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                }
                is ColorTextItem -> {
                    Text(
                        text = item.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFEAEB))
                            .padding(12.dp)
                    )
                }
                is ItalicTextItem -> {
                    Text(
                        text = item.text,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}
