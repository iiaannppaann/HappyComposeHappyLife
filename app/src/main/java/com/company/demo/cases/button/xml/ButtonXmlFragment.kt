package com.company.demo.cases.button.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.button.mvi.ButtonState
import com.company.demo.cases.button.mvi.ButtonViewModel
import com.company.demo.databinding.FragmentButtonDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ButtonXmlFragment : Fragment() {

    private lateinit var binding: FragmentButtonDemoXmlBinding
    private val viewModel: ButtonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentButtonDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 點擊事件綁定至自訂圓角卡片
        binding.btnCardSubmit.setOnClickListener {
            viewModel.submit()
        }

        // 觀察 MVI 狀態流，以指令式方式更新自訂圓角按鈕與內部元件
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.tvBtnText.text = state.text
                
                // 設定點擊啟用狀態與視覺不透明度 (Disabled style feedback)
                val isIdle = state.state == ButtonState.Idle
                binding.btnCardSubmit.isEnabled = isIdle
                binding.btnCardSubmit.alpha = if (isIdle) 1.0f else 0.6f

                when (state.state) {
                    is ButtonState.Idle -> {
                        binding.progressLoading.visibility = View.GONE
                        binding.imgSuccess.visibility = View.GONE
                    }
                    is ButtonState.Loading -> {
                        // 進度圈與文字皆位於卡片圓角背景內部
                        binding.progressLoading.visibility = View.VISIBLE
                        binding.imgSuccess.visibility = View.GONE
                    }
                    is ButtonState.Success -> {
                        // 成功圖示與文字皆位於卡片圓角背景內部
                        binding.progressLoading.visibility = View.GONE
                        binding.imgSuccess.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
