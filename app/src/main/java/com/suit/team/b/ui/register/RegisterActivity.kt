package com.suit.team.b.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.auth.AuthActivity
import com.suit.team.b.utils.string
import com.suit.team.b.utils.text

class RegisterActivity : AppCompatActivity(), RegisterNavigator {

    private val etUsername: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etUsername) }
    private val etPassword: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etPassword) }
    private val etEmail: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.etEmail) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val factory = RegisterViewModel.Factory(ApiModule.authService)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        viewModel.navigator = this

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val registerRequest = RegisterRequest(username = etUsername.text(),
                password = etPassword.text(),
                email = etEmail.text())
            viewModel.register(registerRequest)
        }
    }

    override fun onError(msg: String, errCode: Int) {
        val errMsg = when (errCode) {
            0 -> {
                when (msg.toInt()) {
                    R.string.username_validation -> etUsername.error = string(msg.toInt())
                    R.string.password_validation -> etPassword.error = string(msg.toInt())
                    R.string.email_validation -> etEmail.error = string(msg.toInt())
                    R.string.email_format_validation -> etEmail.error = string(msg.toInt())
                }
                string(msg.toInt())
            }
            400 -> string(R.string.email_format_validation)
            422 -> when {
                msg.contains("username_1 dup key") -> {
                    etUsername.error = string(R.string.username_exist)
                    string(R.string.username_exist)
                }
                msg.contains("email_1 dup key") -> {
                    etEmail.error = string(R.string.email_exist)
                    string(R.string.email_exist)
                }
                msg.contains("alphanumeric") -> {
                    etUsername.error = string(R.string.username_not_alphanumeric)
                    string(R.string.username_not_alphanumeric)
                }
                msg.contains("shorter") -> {
                    etUsername.error = string(R.string.username_shorter)
                    string(R.string.username_shorter)
                }
                else -> msg
            }
            else -> msg
        }

        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, string(msg.toInt()), Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
