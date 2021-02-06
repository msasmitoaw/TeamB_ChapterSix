package com.suit.team.b.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.databinding.ActivityRegisterBinding
import com.suit.team.b.ui.auth.AuthActivity
import com.suit.team.b.utils.string

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val factory = RegisterViewModel.Factory(ApiModule.service)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        bind.btnRegister.setOnClickListener {
            bind.tilEmail.error = null
            bind.tilPassword.error = null
            bind.tilUsername.error = null

            if (bind.etUsername.text!!.isBlank()) {
                bind.tilUsername.error = string(R.string.username_validation)
                return@setOnClickListener
            }
            if (bind.etPassword.text!!.isBlank()) {
                bind.tilPassword.error = string(R.string.password_validation)
                return@setOnClickListener
            }
            if (bind.etEmail.text!!.isBlank()) {
                bind.tilEmail.error = string(R.string.email_validation)
                return@setOnClickListener
            }
            if (bind.etUsername.text!!.length < 6) {
                bind.tilUsername.error = string(R.string.username_shorter)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(bind.etEmail.text!!).matches()) {
                bind.tilEmail.error =
                    string(R.string.email_format_validation)
                return@setOnClickListener
            }

            val registerRequest = RegisterRequest(
                username = bind.etUsername.text.toString(),
                password = bind.etPassword.text.toString(),
                email = bind.etEmail.text.toString()
            )

            it.isEnabled = false
            bind.lottieRegisterLoading.visibility = View.VISIBLE
            bind.lottieRegisterLoading.playAnimation()
            viewModel.register(registerRequest)
        }

        viewModel.onErrorRegister().observe(this, {
            when (it) {
                R.string.username_exist.toString() -> bind.tilUsername.error =
                    string(R.string.username_exist)
                R.string.email_exist.toString() -> bind.tilEmail.error =
                    string(R.string.email_exist)
                R.string.username_not_alphanumeric.toString() -> bind.tilUsername.error =
                    string(R.string.username_not_alphanumeric)
                R.string.username_shorter.toString() -> bind.tilUsername.error =
                    string(R.string.username_shorter)
                else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
            onAnimationFinish()
        })

        viewModel.onResultRegister().observe(this, {
            onAnimationFinish()
            Toast.makeText(this, string(R.string.sign_up_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun onAnimationFinish() {
        bind.lottieRegisterLoading.pauseAnimation()
        bind.lottieRegisterLoading.visibility = View.INVISIBLE
        bind.btnRegister.isEnabled = true
    }
}
