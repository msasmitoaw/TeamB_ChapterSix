package com.suit.team.b.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.suit.team.b.R
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.slide.SlideActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val factory = SplashViewModel.Factory()
        val viewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        viewModel.checkIsLogin()

        val lottie = findViewById<LottieAnimationView>(R.id.lottie_splash)

        lottie.apply {
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) =
                    if (viewModel.checkIsLogin()) onLogged() else unLogged()

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
        }
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
