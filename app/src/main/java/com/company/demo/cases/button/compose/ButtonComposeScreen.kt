package com.company.demo.cases.button.compose

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
import com.company.demo.cases.button.mvi.ButtonState
import com.company.demo.cases.button.mvi.ButtonUiState

@Composable
fun ButtonComposeScreen(
    uiState: ButtonUiState,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = uiState.state == ButtonState.Idle
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (uiState.state) {
                    is ButtonState.Loading -> {
                        CircularProgressIndicator(
                            color = androidx.compose.material3.LocalContentColor.current,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    is ButtonState.Success -> {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    is ButtonState.Idle -> {
                        // 不需要額外圖示
                    }
                }
                Text(text = uiState.text)
            }
        }
    }
}
