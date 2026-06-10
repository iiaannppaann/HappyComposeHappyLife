package com.company.demo.cases.interop.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.company.demo.cases.interop.mvi.InteropViewModel

class InteropComposeFragment : Fragment() {

    private val viewModel: InteropViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                InteropComposeScreen(
                    uiState = uiState,
                    onIncrement = { viewModel.incrementCount() }
                )
            }
        }
    }
}
