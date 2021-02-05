package com.suit.team.b.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.suit.team.b.R
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.slide.SlideActivity

class SplashActivity : AppCompatActivity(), SplashView {
    private var presenter: SplashPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        /*Handler(Looper.getMainLooper()).postDelayed({
            presenter = SplashPresenterImp(this)
            presenter?.checkIsLogin()
        }, 1500)*/
        val lottie = findViewById<LottieAnimationView>(R.id.lottie_splash)

        lottie.apply {
            addAnimatorListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    presenter = SplashPresenterImp(this@SplashActivity)
                    presenter?.checkIsLogin()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
        }
    }

    override fun onLogged() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun unLogged() {
        startActivity(Intent(this, SlideActivity::class.java))
        finish()
    }
}
