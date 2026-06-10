package com.company.demo.cases.tabs.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.company.demo.R
import com.company.demo.cases.tabs.mvi.TabsViewModel
import com.company.demo.databinding.FragmentTabsDemoXmlBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TabsXmlFragment : Fragment() {

    private lateinit var binding: FragmentTabsDemoXmlBinding
    private val viewModel: TabsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsDemoXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 ViewPager2 的介面適配器 (Boilerplate-heavy XML Adapter Pattern)
        val adapter = TabsAdapter()
        binding.viewPager.adapter = adapter

        // 使用 TabLayoutMediator 將 TabLayout 與 ViewPager2 同步
        val mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val state = viewModel.uiState.value
            tab.text = state.tabTitles[position]
        }
        mediator.attach()

        // ViewPager2 滑動監聽，同步選取的分頁到 MVI 狀態中
        binding.viewPager.registerOnPageChangeCallback(object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.selectTab(position)
            }
        })

        // 觀察 MVI 狀態流，同步更新 ViewPager 選取分頁
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitList(state.tabTitles)
                if (binding.viewPager.currentItem != state.selectedIndex) {
                    binding.viewPager.setCurrentItem(state.selectedIndex, true)
                }
            }
        }
    }

    // 傳統 XML 必備的 RecyclerView Adapter 樣板程式碼
    private class TabsAdapter : RecyclerView.Adapter<TabsAdapter.ViewHolder>() {
        private val list = mutableListOf<String>()

        fun submitList(newList: List<String>) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tab_page, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvContent.text = "內容：${list[position]}"
        }

        override fun getItemCount(): Int = list.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        }
    }
}
