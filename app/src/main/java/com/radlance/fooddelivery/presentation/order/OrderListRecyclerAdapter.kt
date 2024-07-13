package com.radlance.fooddelivery.presentation.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ItemOrderBinding
import com.radlance.fooddelivery.domain.entity.CartItem
import com.squareup.picasso.Picasso

class OrderListRecyclerAdapter :
    RecyclerView.Adapter<OrderListRecyclerAdapter.OrderListRecyclerVH>() {
    var orderList = listOf<CartItem>()
        set(value) {
            val callback = OrderListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    fun updateItemCount(cartItem: CartItem) {
        val index = orderList.indexOfFirst { it.product.id == cartItem.product.id }
        val arrayList = orderList.toMutableList()
        arrayList[index] = cartItem

        orderList = arrayList
        notifyItemChanged(index)

    }

    var incrementButtonClickListener: ((CartItem) -> Unit)? = null
    var decrementButtonClickListener: ((CartItem) -> Unit)? = null

    class OrderListRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemOrderBinding.bind(itemView)
        fun bind(cartItem: CartItem) {
            Picasso.get().load(cartItem.product.imageUrl).into(binding.ivProduct)
            binding.tvTitle.text = cartItem.product.title
            binding.tvPrice.text = (cartItem.product.price * cartItem.count).toString()
            binding.tvCount.text = cartItem.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListRecyclerVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderListRecyclerVH(view)
    }

    override fun onBindViewHolder(holder: OrderListRecyclerVH, position: Int) {
        val order = orderList[position]
        holder.binding.buttonPlus.setOnClickListener {
            incrementButtonClickListener?.invoke(order)
        }

        holder.binding.buttonMinus.setOnClickListener {
            decrementButtonClickListener?.invoke(order)
        }
        holder.bind(order)
    }

    override fun getItemCount(): Int = orderList.size
}