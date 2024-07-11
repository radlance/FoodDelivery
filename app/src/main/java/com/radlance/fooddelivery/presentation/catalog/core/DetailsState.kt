package com.radlance.fooddelivery.presentation.catalog.core

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.domain.entity.Product
import com.squareup.picasso.Picasso

interface DetailsState {
    fun show(
        cardView: CardView,
        recyclerView: RecyclerView,
        title: TextView,
        price: TextView,
        image: ImageView
    )

    class Opened(val product: Product) : DetailsState {
        override fun show(
            cardView: CardView,
            recyclerView: RecyclerView,
            title: TextView,
            price: TextView,
            image: ImageView
        ) {
            cardView.visibility = VISIBLE
            recyclerView.visibility = GONE
            title.text = product.title
            price.text = product.price.toString()
            Picasso.get().load(product.imageUrl).into(image)
        }

    }

    object Closed : DetailsState {
        override fun show(
            cardView: CardView,
            recyclerView: RecyclerView,
            title: TextView,
            price: TextView,
            image: ImageView
        ) {
            cardView.visibility = GONE
            recyclerView.visibility = VISIBLE
        }
    }
}