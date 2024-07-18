package com.radlance.fooddelivery.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ItemHistoryBinding
import com.radlance.fooddelivery.domain.entity.HistoryItem

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryRecyclerVH>() {
    var history = listOf<HistoryItem>()
        set(value) {
            val diffCallback = HistoryDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    var onHistoryItemClickListener: ((HistoryItem) -> Unit)? = null

    class HistoryRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemHistoryBinding.bind(itemView)
        fun bind(historyItem: HistoryItem) {
            binding.tvCost.text =
                historyItem.productDeliveries.sumOf { it.product.price * it.count }.toInt()
                    .toString()
            binding.tvAddress.text = setOrderAddress(
                historyItem.street,
                historyItem.house
            )
            binding.tvDate.text = setOrderDate(historyItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryRecyclerVH(view)
    }

    override fun onBindViewHolder(holder: HistoryRecyclerVH, position: Int) {
        val historyItem = history[position]
        holder.itemView.setOnClickListener {
            onHistoryItemClickListener?.invoke(historyItem)
        }
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int = history.size
}