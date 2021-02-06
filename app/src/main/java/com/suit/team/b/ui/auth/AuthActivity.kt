package com.suit.team.b.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.LoginRequest
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.databinding.ActivityAuthBinding
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.register.RegisterActivity
import com.suit.team.b.utils.string

class AuthActivity : AppCompatActivity() {
    private lateinit var bind: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val factory = AuthViewModel.Factory(ApiModule.service, SharedPref)
        val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.checkIsLogin()
        viewModel.onCheckLogin().observe(this, { onSuccess() })

        bind.btnLogin.setOnClickListener {
            bind.tilEmail.error = null
            bind.tilPassword.error = null

            if (bind.etEmail.text!!.isBlank()) {
                bind.tilEmail.error = string(R.string.email_validation)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(bind.etEmail.text!!).matches()) {
                bind.tilEmail.error =
                    string(R.string.email_format_validation)
                return@setOnClickListener
            }
            if (bind.etPassword.text!!.isBlank()) {
                bind.tilPassword.error = string(R.string.password_validation)
                return@setOnClickListener
            }

            it.isEnabled = false
            bind.lottieLoginLoading.visibility = View.VISIBLE
            bind.lottieLoginLoading.playAnimation()
            viewModel.login(
                LoginRequest(
                    email = bind.etEmail.text.toString(),
                    password = bind.etPassword.text.toString()
                )
            )
        }

        bind.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.onErrorLogin().observe(this, {
            when (it) {
                R.string.wrong_email.toString() -> bind.tilEmail.error =
                    string(R.string.wrong_email)
                R.string.wrong_password.toString() -> bind.tilPassword.error =
                    string(R.string.wrong_password)
                else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
            onAnimationFinish()
        })

        viewModel.onResultLogin().observe(this, {
            onSuccess()
            onAnimationFinish()
        })
    }

    private fun onAnimationFinish() {
        bind.lottieLoginLoading.pauseAnimation()
        bind.lottieLoginLoading.visibility = View.INVISIBLE
        bind.btnLogin.isEnabled = true
    }

    private fun onSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
