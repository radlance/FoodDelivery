package com.radlance.fooddelivery.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductCache::class, CategoryCache::class, CartItemCache::class], version = 1)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun productsDao(): DeliveryDao

    companion object {
        private var INSTANCE: DeliveryDatabase? = null
        fun newInstance(context: Context): DeliveryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeliveryDatabase::class.java,
                    "delivery_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}