package com.radlance.fooddelivery.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ItemHistoryBinding
import com.radlance.fooddelivery.domain.entity.HistoryItem
import java.text.SimpleDateFormat
import java.util.Locale

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
                historyItem.house
            )

            val dateString = historyItem.orderTime
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

            val date = inputFormat.parse(dateString)
            val outputDateString = date?.let { outputFormat.format(it) }
            binding.tvDate.text = outputDateString
        }

        private fun setAddress(street: String, house: Int): String {
            return "$street, $house"
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