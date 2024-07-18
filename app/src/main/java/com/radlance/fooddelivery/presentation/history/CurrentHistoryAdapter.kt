package com.radlance.fooddelivery.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ItemCurrentHistoryBinding
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.presentation.order.OrderListDiffCallback
import com.squareup.picasso.Picasso

class CurrentHistoryAdapter : RecyclerView.Adapter<CurrentHistoryAdapter.CurrentHistoryVH>() {
    var orderList = listOf<CartItem>()
        set(value) {
            val callback = OrderListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    class CurrentHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCurrentHistoryBinding.bind(itemView)

        fun bind(cartItem: CartItem) {
            Picasso.get().load(cartItem.product.imageUrl).into(binding.ivProduct)
            binding.tvTitle.text = cartItem.product.title
            binding.tvPrice.text = (cartItem.product.price * cartItem.count).toInt().toString()
            binding.tvCount.text = cartItem.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentHistoryVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_current_history, parent, false)
        return CurrentHistoryVH(view)
    }

    override fun onBindViewHolder(holder: CurrentHistoryVH, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.size
}