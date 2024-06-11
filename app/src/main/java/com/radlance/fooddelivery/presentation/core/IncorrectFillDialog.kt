package com.radlance.fooddelivery.presentation.core

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class IncorrectFillDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(requireArguments().getString(ERROR_KEY))
            .setPositiveButton("OK", null)
        return builder.create()
    }

    companion object {
        private const val ERROR_KEY = "error"
        fun newInstance(message: String): IncorrectFillDialog {
            return IncorrectFillDialog().apply {
                arguments = Bundle().apply {
                    putString(ERROR_KEY, message)
                }
            }
        }
    }
}