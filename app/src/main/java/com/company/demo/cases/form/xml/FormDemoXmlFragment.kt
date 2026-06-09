package com.company.demo.cases.form.xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.company.demo.cases.form.mvi.FormDemoViewModel
import com.company.demo.databinding.FragmentFormDemoXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FormDemoXmlFragment : Fragment() {

    private lateinit var binding: FragmentFormDemoXmlBinding
    private val viewModel: FormDemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 設定工具列
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 設定輸入框內容變更監聽器
        binding.etField1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateField("name", s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etField2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateField("email", s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSubmit.setOnClickListener {
            viewModel.submitForm()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                // 設定 Label 文字
                state.fields.find { it.id == "name" }?.let { field ->
                    binding.tvLabel1.text = field.label
                    if (binding.etField1.text.toString() != field.value) {
                        binding.etField1.setText(field.value)
                        binding.etField1.setSelection(field.value.length)
                    }
                }

                state.fields.find { it.id == "email" }?.let { field ->
                    binding.tvLabel2.text = field.label
                    if (binding.etField2.text.toString() != field.value) {
                        binding.etField2.setText(field.value)
                        binding.etField2.setSelection(field.value.length)
                    }
                }

                binding.tvSubmitResult.text = state.submitResult
            }
        }
    }
}
