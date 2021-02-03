package com.suit.team.b.ui.register

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
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.auth.AuthActivity
import com.suit.team.b.utils.string
import com.suit.team.b.utils.text

class RegisterActivity : AppCompatActivity() {
    private val etUsername: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etUsername) }
    private val etPassword: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etPassword) }
    private val etEmail: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etEmail) }
    private val tilUsername: TextInputLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tilUsername) }
    private val tilPassword: TextInputLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tilPassword) }
    private val tilEmail: TextInputLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tilEmail) }
    private val btnRegister: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.btnRegister) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val factory = RegisterViewModel.Factory(ApiModule.service)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        viewModel.onErrorRegister().observe(this, {
            when (it) {
                R.string.username_exist.toString() -> tilUsername.error =
                    string(R.string.username_exist)
                R.string.email_exist.toString() -> tilEmail.error = string(R.string.email_exist)
                R.string.username_not_alphanumeric.toString() -> tilUsername.error =
                    string(R.string.username_not_alphanumeric)
                R.string.username_shorter.toString() -> tilUsername.error =
                    string(R.string.username_shorter)
                else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.onResultRegister().observe(this, {
            Toast.makeText(this, string(R.string.sign_up_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        })

        btnRegister.setOnClickListener {
            tilEmail.error = null
            tilPassword.error = null
            tilUsername.error = null

            if (etUsername.text.isBlank()) {
                tilUsername.error = string(R.string.username_validation)
                return@setOnClickListener
            }
            if (etPassword.text.isBlank()) {
                tilPassword.error = string(R.string.password_validation)
                return@setOnClickListener
            }
            if (etEmail.text.isBlank()) {
                tilEmail.error = string(R.string.email_validation)
                return@setOnClickListener
            }
            if (etUsername.text.length < 6) {
                tilUsername.error = string(R.string.username_shorter)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()) {
                tilEmail.error =
                    string(R.string.email_format_validation)
                return@setOnClickListener
            }

            val registerRequest = RegisterRequest(username = etUsername.text(),
                password = etPassword.text(),
                email = etEmail.text())
            viewModel.register(registerRequest)
        }
    }
}
