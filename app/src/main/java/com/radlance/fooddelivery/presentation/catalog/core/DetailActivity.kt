package com.radlance.fooddelivery.presentation.catalog.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.databinding.ActivityDetailBinding
import com.radlance.fooddelivery.domain.entity.Product
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)!!

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvTitle.text = product.title
        binding.tvPrice.text = product.price.toString()
        Picasso.get().load(product.imageUrl).into(binding.ivProduct)
    }

    companion object {
        private const val EXTRA_PRODUCT = "product"

        fun productInstance(context: Context, product: Product): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            return intent
        }
    }
}