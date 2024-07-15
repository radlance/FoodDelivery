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

    class HistoryRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemHistoryBinding.bind(itemView)
        fun bind(historyItem: HistoryItem) {
            binding.tvCost.text =
                historyItem.productDeliveries.sumOf { it.product.price }.toInt().toString()
            binding.tvAddress.text = setAddress(
                historyItem.street,
                historyItem.house,
                historyItem.building,
                historyItem.apartment
            )

            binding.tvDate.text = historyItem.orderTime
        }

        private fun setAddress(street: String, house: Int, building: Int, apartment: Int): String {
            return "$street, $house-$building-$apartment"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryRecyclerVH(view)
    }

    override fun onBindViewHolder(holder: HistoryRecyclerVH, position: Int) {
        val historyItem = history[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int = history.size
}