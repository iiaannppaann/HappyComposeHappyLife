package com.company.demo.cases.nested.compose

import com.company.demo.cases.nested.mvi.NestedDemoUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NestedDemoComposeScreen(
    uiState: NestedDemoUiState,
    onTriggerStressTest: (Int, Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Compose 壓測按鈕
        Button(
            onClick = {
                val startTime = System.currentTimeMillis()
                
                // 1. 同樣進行 5000 次連續的高頻狀態變更
                for (i in 1..5000) {
                    onTriggerStressTest(i, 0)
                }
                
                // 2. 測量最後一刻的總耗時
                val duration = System.currentTimeMillis() - startTime
                onTriggerStressTest(5000, duration)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "開始高頻率重繪壓測（5000次）")
        }

        if (uiState.stressResult.isNotEmpty()) {
            Text(
                text = uiState.stressResult,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 10層巢狀 Column
        // Level 1
        Text(text = uiState.level1, modifier = Modifier.padding(bottom = 8.dp))
        
        // Level 2
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = uiState.level2, modifier = Modifier.padding(bottom = 8.dp))
            
            // Level 3
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = uiState.level3, modifier = Modifier.padding(bottom = 8.dp))
                
                // Level 4
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = uiState.level4, modifier = Modifier.padding(bottom = 8.dp))
                    
                    // Level 5
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(text = uiState.level5, modifier = Modifier.padding(bottom = 8.dp))
                        
                        // Level 6
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(text = uiState.level6, modifier = Modifier.padding(bottom = 8.dp))
                            
                            // Level 7
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(text = uiState.level7, modifier = Modifier.padding(bottom = 8.dp))
                                
                                // Level 8
                                Column(modifier = Modifier.padding(start = 16.dp)) {
                                    Text(text = uiState.level8, modifier = Modifier.padding(bottom = 8.dp))
                                    
                                    // Level 9
                                    Column(modifier = Modifier.padding(start = 16.dp)) {
                                        Text(text = uiState.level9, modifier = Modifier.padding(bottom = 8.dp))
                                        
                                        // Level 10
                                        Column(modifier = Modifier.padding(start = 16.dp)) {
                                            Text(text = uiState.level10, modifier = Modifier.padding(bottom = 8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
