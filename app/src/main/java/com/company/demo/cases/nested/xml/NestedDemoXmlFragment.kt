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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                // 將 10 層資料各自綁定到 10 層巢狀 LinearLayout 中的 TextView 上
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
            }
        }
    }
}
