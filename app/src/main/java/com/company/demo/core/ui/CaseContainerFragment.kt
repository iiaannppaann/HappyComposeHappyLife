package com.company.demo.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.company.demo.core.DemoCase

class CaseContainerFragment : Fragment() {

    private lateinit var demoCase: DemoCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val caseName = arguments?.getString(ARG_CASE_NAME)
            ?: throw IllegalArgumentException("DemoCase name is required")
        demoCase = DemoCase.valueOf(caseName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    CaseContainerScreen(
                        demoCase = demoCase,
                        onBack = { parentFragmentManager.popBackStack() }
                    )
                }
            }
        }
    }

    companion object {
        private const val ARG_CASE_NAME = "arg_case_name"

        fun newInstance(demoCase: DemoCase): CaseContainerFragment {
            return CaseContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CASE_NAME, demoCase.name)
                }
            }
        }
    }
}
