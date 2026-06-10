package com.company.demo.cases.customview.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.customview.mvi.CustomViewDemoViewModel
import com.company.demo.databinding.FragmentCustomViewDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CustomViewDemoXmlFragment : Fragment() {

    private lateinit var binding: FragmentCustomViewDemoXmlBinding
    private val viewModel: CustomViewDemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomViewDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 點擊事件綁定至自訂視圖
        binding.loadingButton.setOnClickListener {
            viewModel.submit()
        }

        // 觀察 MVI 狀態流，同步更新自訂視圖狀態
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.loadingButton.setState(state.state, state.text)
            }
        }
    }
}
