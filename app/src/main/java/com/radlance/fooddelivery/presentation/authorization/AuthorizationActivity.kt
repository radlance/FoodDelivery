package com.radlance.fooddelivery.presentation.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.radlance.fooddelivery.R

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        if (savedInstanceState == null) {
            if (intent.hasExtra(LAUNCH_EXTRA)) {
                when (intent.getStringExtra(LAUNCH_EXTRA)) {
                    SIGN_IN_MODE -> launchNewFragment(SignInFragment.newInstance())
                    SIGN_UP_MODE -> launchNewFragment(SignUpFragment.newInstance())

                    else -> throw IllegalStateException(
                        "Unknown mode ${intent.getStringExtra(LAUNCH_EXTRA)}"
                    )
                }
            }
        }
    }

    private fun launchNewFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container_auth, fragment)
            .commit()
    }

    companion object {
        private const val LAUNCH_EXTRA = "launch_mode"
        private const val SIGN_IN_MODE = "sign_in"
        private const val SIGN_UP_MODE = "sign_up"
        fun newInstanceSignIn(context: Context): Intent {
            return Intent(context, AuthorizationActivity::class.java).apply {
                putExtra(LAUNCH_EXTRA, SIGN_IN_MODE)
            }
        }

        fun newInstanceSignUp(context: Context): Intent {
            return Intent(context, AuthorizationActivity::class.java).apply {
                putExtra(LAUNCH_EXTRA, SIGN_UP_MODE)
            }
        }
    }
}