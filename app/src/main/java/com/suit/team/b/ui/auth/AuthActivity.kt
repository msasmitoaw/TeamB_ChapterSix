package com.suit.team.b.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.LoginRequest
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.register.RegisterActivity
import com.suit.team.b.utils.string
import com.suit.team.b.utils.text

class AuthActivity : AppCompatActivity() {
    private val etEmail: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etEmail) }
    private val etPassword: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etPassword) }
    private val tilEmail: TextInputLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tilEmail) }
    private val tilPassword: TextInputLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tilPassword) }
    private val btnRegister: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.btnRegister) }
    private val btnLogin: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.btnLogin) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val factory = AuthViewModel.Factory(ApiModule.service, SharedPref)
        val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.checkIsLogin()
        viewModel.onCheckLogin().observe(this, { onSuccess() })

        btnLogin.setOnClickListener {
            tilEmail.error = null
            tilPassword.error = null

            if (etEmail.text.isBlank()) {
                tilEmail.error = string(R.string.email_validation)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()) {
                tilEmail.error =
                    string(R.string.email_format_validation)
                return@setOnClickListener
            }
            if (etPassword.text.isBlank()) {
                tilPassword.error = string(R.string.password_validation)
                return@setOnClickListener
            }

            viewModel.login(LoginRequest(email = etEmail.text(), password = etPassword.text()))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        viewModel.onErrorLogin().observe(this, {
            when (it) {
                R.string.wrong_email.toString() -> tilEmail.error = string(R.string.wrong_email)
                R.string.wrong_password.toString() -> tilPassword.error =
                    string(R.string.wrong_password)
                else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.onResultLogin().observe(this, { onSuccess() })
    }

    private fun onSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
