package com.company.demo.cases.multitype.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.company.demo.cases.multitype.mvi.ListItem
import com.company.demo.cases.multitype.mvi.MultiTypeViewModel
import com.company.demo.cases.multitype.mvi.PlainTextItem
import com.company.demo.cases.multitype.mvi.ColorTextItem
import com.company.demo.cases.multitype.mvi.ItalicTextItem
import com.company.demo.databinding.FragmentMultiTypeXmlBinding
import com.company.demo.databinding.ItemPlainBinding
import com.company.demo.databinding.ItemColorBinding
import com.company.demo.databinding.ItemItalicBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MultiTypeXmlFragment : Fragment() {

    private lateinit var binding: FragmentMultiTypeXmlBinding
    private val viewModel: MultiTypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMultiTypeXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MultiTypeAdapter()
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitList(state.items)
            }
        }
    }

    // 傳統 Android 中的 RecyclerView 多樣式清單 Adapter 實作
    // 必須手動維護 ItemViewType、重寫 onCreate/onBind 以及撰寫大量的 ViewHolder 樣板程式碼
    private class MultiTypeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val items = mutableListOf<ListItem>()

        fun submitList(newItems: List<ListItem>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        // 1. 根據項目的型態決定 ViewType，此處有三種樣式
        override fun getItemViewType(position: Int): Int {
            return when (items[position]) {
                is PlainTextItem -> TYPE_PLAIN
                is ColorTextItem -> TYPE_COLOR
                is ItalicTextItem -> TYPE_ITALIC
            }
        }

        // 2. 根據 ViewType 加載各自的佈局，並實例化對應的 ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                TYPE_PLAIN -> {
                    val binding = ItemPlainBinding.inflate(inflater, parent, false)
                    PlainViewHolder(binding)
                }
                TYPE_COLOR -> {
                    val binding = ItemColorBinding.inflate(inflater, parent, false)
                    ColorViewHolder(binding)
                }
                TYPE_ITALIC -> {
                    val binding = ItemItalicBinding.inflate(inflater, parent, false)
                    ItalicViewHolder(binding)
                }
                else -> throw IllegalArgumentException("未知的 viewType: $viewType")
            }
        }

        // 3. 將資料與特定的 ViewHolder 進行動態綁定
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = items[position]
            when (holder) {
                is PlainViewHolder -> {
                    val plainItem = item as PlainTextItem
                    holder.binding.tvPlain.text = plainItem.text
                }
                is ColorViewHolder -> {
                    val colorItem = item as ColorTextItem
                    holder.binding.tvColor.text = colorItem.text
                }
                is ItalicViewHolder -> {
                    val italicItem = item as ItalicTextItem
                    holder.binding.tvItalic.text = italicItem.text
                }
            }
        }

        override fun getItemCount(): Int = items.size

        // 4. 定義三種對應的 ViewHolder 樣板
        class PlainViewHolder(val binding: ItemPlainBinding) : RecyclerView.ViewHolder(binding.root)
        class ColorViewHolder(val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root)
        class ItalicViewHolder(val binding: ItemItalicBinding) : RecyclerView.ViewHolder(binding.root)

        companion object {
            private const val TYPE_PLAIN = 0
            private const val TYPE_COLOR = 1
            private const val TYPE_ITALIC = 2
        }
    }
}
