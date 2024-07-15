package com.radlance.fooddelivery.presentation.authorization

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.FragmentActivity
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.authorization.SignUpFragment.Companion.ERROR_DIALOG
import com.radlance.fooddelivery.presentation.core.IncorrectFillDialog
import com.radlance.fooddelivery.presentation.main.MainActivity

interface RegistrationState {
    fun show(
        activity: FragmentActivity,
        progressBar: ProgressBar,
        textView: TextView,
        button: Button
    )

    class Success(private val token: String) : RegistrationState {
        override fun show(
            activity: FragmentActivity,
            progressBar: ProgressBar,
            textView: TextView,
            button: Button
        ) {
            val intent = MainActivity.newInstance(activity.applicationContext, token)
            activity.startActivity(intent)
        }
    }

    object Loading : RegistrationState {
        override fun show(
            activity: FragmentActivity,
            progressBar: ProgressBar,
            textView: TextView,
            button: Button
        ) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
            button.isEnabled = false
        }
    }

    object Error : RegistrationState {
        override fun show(
            activity: FragmentActivity,
            progressBar: ProgressBar,
            textView: TextView,
            button: Button
        ) {
            IncorrectFillDialog.newInstance(
                getString(
                    activity.applicationContext,
                    R.string.failed_registration
                )
            )
                .show(activity.supportFragmentManager, ERROR_DIALOG)
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            button.isEnabled = true
        }
    }
}