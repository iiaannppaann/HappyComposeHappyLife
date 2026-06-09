package com.company.demo.cases.nested.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.nested.mvi.NestedDemoViewModel
import com.company.demo.databinding.FragmentNestedDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NestedDemoXmlFragment : Fragment() {

    private lateinit var binding: FragmentNestedDemoXmlBinding
    private val viewModel: NestedDemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNestedDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 高頻率重新測量與排版壓測按鈕
        binding.btnStressTest.setOnClickListener {
            binding.tvStressResult.text = "壓測開始，主執行緒卡死中..."
            
            // 透過 post 確保 UI 顯示「壓測開始」字樣後，立刻阻塞主執行緒進行密集測量
            binding.tvStressResult.post {
                val startTime = System.currentTimeMillis()
                
                for (i in 1..5000) {
                    binding.tvLevel10.text = "【第 10 層】壓測次數: $i"
                    
                    // 【效能致命關鍵】：在 10 層 LinearLayout 嵌套下，每一次手動調用 measure & layout 
                    // 都會強制整個佈局樹自上而下進行指數級的測量與排版，5000 次連續的同步操作
                    // 會直接阻塞 Android 主執行緒（UI Thread）長達數秒，從而引起嚴重的畫面凍結甚至 ANR！
                    binding.root.measure(
                        View.MeasureSpec.makeMeasureSpec(binding.root.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(binding.root.height, View.MeasureSpec.EXACTLY)
                    )
                    binding.root.layout(binding.root.left, binding.root.top, binding.root.right, binding.root.bottom)
                }
                
                val duration = System.currentTimeMillis() - startTime
                val resultText = "壓測完成！耗時: ${duration}ms (主執行緒凍結時間)"
                binding.tvStressResult.text = resultText
                viewModel.setStressResult(resultText)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.tvLevel1.text = state.level1
                binding.tvLevel2.text = state.level2
                binding.tvLevel3.text = state.level3
                binding.tvLevel4.text = state.level4
                binding.tvLevel5.text = state.level5
                binding.tvLevel6.text = state.level6
                binding.tvLevel7.text = state.level7
                binding.tvLevel8.text = state.level8
                binding.tvLevel9.text = state.level9
                binding.tvLevel10.text = state.level10
                binding.tvStressResult.text = state.stressResult
            }
        }
    }
}
