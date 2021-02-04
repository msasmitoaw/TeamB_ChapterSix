package com.suit.team.b.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.slide.SlideActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val factory = SplashViewModel.Factory(ApiModule.service, SharedPref)
        val viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.checkIsLogin()) onLogged() else unLogged()
        }, 1500)

        viewModel.onCheckLogin().observe(this, { if (it) onLogged() else unLogged() })
    }

    private fun onLogged() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun unLogged() {
        startActivity(Intent(this, SlideActivity::class.java))
        finish()
    }
}
