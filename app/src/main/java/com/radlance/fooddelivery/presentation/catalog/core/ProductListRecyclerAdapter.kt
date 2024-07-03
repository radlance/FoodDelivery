package com.radlance.fooddelivery.presentation.catalog.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ItemProductBinding
import com.radlance.fooddelivery.domain.entity.Product
import com.squareup.picasso.Picasso

class ProductListRecyclerAdapter :
    RecyclerView.Adapter<ProductListRecyclerAdapter.ProductListVH>() {
    var productList = listOf<Product>()
        set(value) {
            val callback = ProductListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    var onProductItemClickListener: ((Product) -> Unit)? = null

    class ProductListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductBinding.bind(itemView)
        fun bind(product: Product) {
            with(binding) {
                tvTitle.text = product.title
                tvPrice.text = product.price.toString()
                Picasso.get().load(product.imageUrl).into(binding.ivProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductListVH(view)
    }

    override fun onBindViewHolder(holder: ProductListVH, position: Int) {
        val product = productList[position]

        holder.itemView.setOnClickListener {
            onProductItemClickListener?.invoke(product)
        }

        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size
}