package com.radlance.fooddelivery.presentation.history

import androidx.recyclerview.widget.DiffUtil
import com.radlance.fooddelivery.domain.entity.HistoryItem

class HistoryDiffCallback(
    private val oldList: List<HistoryItem>,
    private val newList: List<HistoryItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[oldItemPosition]
    }
}