package com.company.demo.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.company.demo.R
import com.company.demo.cases.background.compose.BackgroundDemoComposeFragment
import com.company.demo.cases.background.xml.BackgroundDemoXmlFragment
import com.company.demo.cases.nested.compose.NestedDemoComposeFragment
import com.company.demo.cases.nested.xml.NestedDemoXmlFragment
import com.company.demo.cases.form.compose.FormDemoComposeFragment
import com.company.demo.cases.form.xml.FormDemoXmlFragment
import com.company.demo.cases.multitype.compose.MultiTypeComposeFragment
import com.company.demo.cases.multitype.xml.MultiTypeXmlFragment
import com.company.demo.cases.customview.compose.CustomViewDemoComposeFragment
import com.company.demo.cases.customview.xml.CustomViewDemoXmlFragment
import com.company.demo.cases.interop.compose.InteropComposeFragment
import com.company.demo.cases.interop.xml.InteropXmlFragment
import com.company.demo.cases.tabs.compose.TabsComposeFragment
import com.company.demo.cases.tabs.xml.TabsXmlFragment
import com.company.demo.core.DemoCase
import com.company.demo.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackgroundXml.setOnClickListener {
            navigateToFragment(BackgroundDemoXmlFragment())
        }
        binding.btnBackgroundCompose.setOnClickListener {
            navigateToFragment(BackgroundDemoComposeFragment())
        }
        binding.btnNestedXml.setOnClickListener {
            navigateToFragment(NestedDemoXmlFragment())
        }
        binding.btnNestedCompose.setOnClickListener {
            navigateToFragment(NestedDemoComposeFragment())
        }
        binding.btnFormXml.setOnClickListener {
            navigateToFragment(FormDemoXmlFragment())
        }
        binding.btnFormCompose.setOnClickListener {
            navigateToFragment(FormDemoComposeFragment())
        }
        binding.btnMultiTypeXml.setOnClickListener {
            navigateToFragment(MultiTypeXmlFragment())
        }
        binding.btnMultiTypeCompose.setOnClickListener {
            navigateToFragment(MultiTypeComposeFragment())
        }
        binding.btnStatefulButtonXml.setOnClickListener {
            navigateToFragment(CustomViewDemoXmlFragment())
        }
        binding.btnStatefulButtonCompose.setOnClickListener {
            navigateToFragment(CustomViewDemoComposeFragment())
        }
        binding.btnInteropXml.setOnClickListener {
            navigateToFragment(InteropXmlFragment())
        }
        binding.btnInteropCompose.setOnClickListener {
            navigateToFragment(InteropComposeFragment())
        }
        binding.btnClassicXml.setOnClickListener {
            navigateToFragment(TabsXmlFragment())
        }
        binding.btnClassicCompose.setOnClickListener {
            navigateToFragment(TabsComposeFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
