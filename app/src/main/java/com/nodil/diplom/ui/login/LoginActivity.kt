package com.nodil.diplom.ui.login

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.nodil.diplom.MainActivity
import com.nodil.diplom.R
import com.nodil.diplom.databinding.ActivityLoginBinding
import com.nodil.diplom.ui.services.LocationService
import com.nodil.diplom.util.PermissionChecker
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by inject()
    private val permissionChecker = PermissionChecker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        permissionChecker.request(
            Manifest.permission.ACCESS_FINE_LOCATION,
            100
        )
        permissionChecker.request(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            100
        )
        permissionChecker.request(
            Manifest.permission.ACCESS_NETWORK_STATE,
            100
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionChecker.request(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                111
            )
        }


        textListeners()
        clickListeners()
        observe()
        binding.loginText.setText("Dima")
        binding.passwordText.setText("123321")

    }

    private fun observe() {
        viewModel.isLoginSuccessful.observe(this) {
            if (it) {
                Snackbar.make(binding.root, "Успешный вход", Snackbar.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                binding.loginText.error = "Неправильный логин"
                binding.passwordText.error = "Неправильный логин "
                Snackbar.make(binding.root, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun textListeners() {
        binding.loginText.doAfterTextChanged { text ->
            viewModel.setLogin(text.toString())
        }
        binding.passwordText.doAfterTextChanged { text ->
            viewModel.setPassword(text.toString())
        }
    }

    private fun clickListeners() {
        binding.elevatedButton.setOnClickListener {
            viewModel.login()
        }
    }
}