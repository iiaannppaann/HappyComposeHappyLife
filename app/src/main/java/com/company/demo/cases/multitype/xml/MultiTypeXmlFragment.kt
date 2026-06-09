package com.company.demo.cases.multitype.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.company.demo.cases.multitype.mvi.HeaderItem
import com.company.demo.cases.multitype.mvi.ListItem
import com.company.demo.cases.multitype.mvi.MultiTypeViewModel
import com.company.demo.cases.multitype.mvi.TextItem
import com.company.demo.databinding.FragmentMultiTypeXmlBinding
import com.company.demo.databinding.ItemHeaderBinding
import com.company.demo.databinding.ItemTextBinding
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

    private class MultiTypeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val items = mutableListOf<ListItem>()

        fun submitList(newItems: List<ListItem>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int): Int {
            return when (items[position]) {
                is HeaderItem -> TYPE_HEADER
                is TextItem -> TYPE_TEXT
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                TYPE_HEADER -> {
                    val binding = ItemHeaderBinding.inflate(inflater, parent, false)
                    HeaderViewHolder(binding)
                }
                TYPE_TEXT -> {
                    val binding = ItemTextBinding.inflate(inflater, parent, false)
                    TextViewHolder(binding)
                }
                else -> throw IllegalArgumentException("Unknown viewType: $viewType")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = items[position]
            when (holder) {
                is HeaderViewHolder -> {
                    val headerItem = item as HeaderItem
                    holder.binding.tvHeaderTitle.text = headerItem.title
                }
                is TextViewHolder -> {
                    val textItem = item as TextItem
                    holder.binding.tvItemText.text = textItem.text
                }
            }
        }

        override fun getItemCount(): Int = items.size

        class HeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root)
        class TextViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)

        companion object {
            private const val TYPE_HEADER = 0
            private const val TYPE_TEXT = 1
        }
    }
}
