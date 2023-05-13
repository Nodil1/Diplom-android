package com.nodil.diplom.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nodil.diplom.BaseActivity
import com.nodil.diplom.R
import com.nodil.diplom.ui.home.HomeActivity
import com.nodil.diplom.ui.login.LoginActivity
import com.nodil.diplom.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.system.exitProcess

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val viewModel : SplashViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1_000)
            runOnUiThread {
                viewModel.load()
            }
        }
        observe()
    }

    private fun observe() {
        viewModel.authState.observe(this) {
            if (it == true) {
                startActivity(
                    Intent(this@SplashActivity, HomeActivity::class.java)
                )
                finish()
            } else if (it == false) {
                startActivity(
                    Intent(this@SplashActivity, LoginActivity::class.java)
                )
                finish()

            }
        }
    }
}