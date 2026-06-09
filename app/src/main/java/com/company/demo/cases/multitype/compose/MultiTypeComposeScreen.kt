package com.company.demo.cases.multitype.compose

import com.company.demo.cases.multitype.mvi.MultiTypeUiState
import com.company.demo.cases.multitype.mvi.HeaderItem
import com.company.demo.cases.multitype.mvi.TextItem
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiTypeComposeScreen(uiState: MultiTypeUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(uiState.items) { item ->
            when (item) {
                is HeaderItem -> {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                }
                is TextItem -> {
                    Text(
                        text = item.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}
