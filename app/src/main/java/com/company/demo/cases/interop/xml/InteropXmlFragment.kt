package com.company.demo.cases.interop.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.interop.mvi.InteropViewModel
import com.company.demo.databinding.FragmentInteropDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InteropXmlFragment : Fragment() {

    private lateinit var binding: FragmentInteropDemoXmlBinding
    private val viewModel: InteropViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInteropDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 綁定 XML 按鈕點擊，呼叫 ViewModel 增加計數值
        binding.btnXmlIncrement.setOnClickListener {
            viewModel.incrementCount()
        }

        // 觀察 MVI 狀態流，同步更新 XML 視圖文字
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.tvXmlCount.text = "當前計數值: ${state.count}"
            }
        }

        // 【混合互操作】：將 ComposeView 嵌入傳統 XML 版面中
        binding.composeViewContainer.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state by viewModel.uiState.collectAsState()
                
                // 這是完全由 Compose 繪製並嵌入 XML 中的內容
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "以下區塊為嵌入 XML 的 Compose 元件：",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Compose 讀取之計數器: ${state.count}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.incrementCount() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = "點我 (來自內嵌 Compose 按鈕)")
                    }
                }
            }
        }
    }
}
