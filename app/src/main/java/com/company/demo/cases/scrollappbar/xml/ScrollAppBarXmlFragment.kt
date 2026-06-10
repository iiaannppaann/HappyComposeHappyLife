package com.company.demo.cases.scrollappbar.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.demo.R
import com.company.demo.cases.scrollappbar.mvi.ScrollAppBarViewModel
import com.company.demo.databinding.FragmentScrollAppbarXmlBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScrollAppBarXmlFragment : Fragment() {

    private lateinit var binding: FragmentScrollAppbarXmlBinding
    private val viewModel: ScrollAppBarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollAppbarXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        val adapter = PostAdapter()
        binding.recyclerView.adapter = adapter

        // 觀察 MVI 狀態流，同步資料到 RecyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitList(state.posts)
            }
        }

        // 傳統 XML 必備的指令式監聽：手動監聽捲動，並根據位置手動改變 View 的可見度與透明度
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                
                // 判斷第一個 Item (假頭像區) 是否已經滑出畫面
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val shouldShowTopBar = firstVisibleItemPosition > 0

                if (shouldShowTopBar) {
                    if (binding.topBar.visibility == View.GONE) {
                        // 繁瑣的手動動畫控制
                        binding.topBar.alpha = 0f
                        binding.topBar.visibility = View.VISIBLE
                        binding.topBar.animate().alpha(1f).setDuration(200).start()
                    }
                } else {
                    if (binding.topBar.visibility == View.VISIBLE) {
                        binding.topBar.animate().alpha(0f).setDuration(200).withEndAction {
                            binding.topBar.visibility = View.GONE
                        }.start()
                    }
                }
            }
        })
    }

    // 傳統 XML 必備的 Adapter 樣板程式碼
    private class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val list = mutableListOf<String>()

        companion object {
            const val TYPE_HEADER = 0
            const val TYPE_POST = 1
        }

        fun submitList(newList: List<String>) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) TYPE_HEADER else TYPE_POST
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == TYPE_HEADER) {
                // 手動建立一個 Header 假頭像區塊
                val headerView = FrameLayout(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600) // ~200dp
                    setBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"))
                    val tv = TextView(context).apply {
                        text = "這是一個假頭像/封面區域\n請向下捲動以顯示 Top Bar"
                        setTextColor(android.graphics.Color.DKGRAY)
                        gravity = android.view.Gravity.CENTER
                        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                            gravity = android.view.Gravity.CENTER
                        }
                    }
                    addView(tv)
                }
                HeaderViewHolder(headerView)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dummy_post, parent, false)
                PostViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is PostViewHolder) {
                // 扣除掉 Header，所以 index 減 1
                holder.tvPostText.text = list[position - 1]
            }
        }

        // 總數包含一個 Header
        override fun getItemCount(): Int = list.size + 1

        class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvPostText: TextView = itemView.findViewById(R.id.tvPostText)
        }
    }
}
