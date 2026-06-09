package com.company.demo.cases.background.compose

import com.company.demo.cases.background.mvi.BackgroundDemoUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun BackgroundDemoComposeScreen(uiState: BackgroundDemoUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "每張卡片的背景、邊框與圓角皆直接從 UiState 資料中使用 Modifier 單行程式碼動態渲染。無需任何外部 XML drawable 檔案！",
            modifier = Modifier.padding(bottom = 24.dp)
        )

        uiState.cards.forEach { card ->
            val bgCol = runCatching { Color(card.backgroundColorHex.toColorInt()) }.getOrDefault(Color.Gray)
            val borderCol = runCatching { Color(card.borderColorHex.toColorInt()) }.getOrDefault(Color.Transparent)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(card.cornerRadiusDp.dp))
                    .background(bgCol)
                    .then(
                        if (card.borderWidthDp > 0) {
                            Modifier.border(card.borderWidthDp.dp, borderCol, RoundedCornerShape(card.cornerRadiusDp.dp))
                        } else {
                            Modifier
                        }
                    )
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = card.text,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
