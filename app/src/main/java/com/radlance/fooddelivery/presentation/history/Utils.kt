package com.radlance.fooddelivery.presentation.history

import com.radlance.fooddelivery.domain.entity.HistoryItem
import java.text.SimpleDateFormat
import java.util.Locale

fun setOrderDate(historyItem: HistoryItem): String? {
    val dateString = historyItem.orderTime
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    val outputDateString = date?.let { outputFormat.format(it) }
    return outputDateString
}

fun setOrderAddress(street: String, house: Int): String {
    return "$street, $house"
}