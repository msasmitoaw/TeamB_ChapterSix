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
import com.suit.team.b.utils.text

class RegisterActivity : AppCompatActivity(), RegisterNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val factory = RegisterViewModel.Factory(ApiModule.authService)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        viewModel.navigator = this

        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)
        val email = findViewById<EditText>(R.id.etEmail)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val registerRequest = RegisterRequest(username = username.text(),
                password = password.text(),
                email = email.text())
            viewModel.register(registerRequest)
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
