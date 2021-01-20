package com.suit.team.b.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suit.team.b.R
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.ui.slide.SlideActivity
import android.os.Handler


class SplashActivity : AppCompatActivity(), SplashView {
    private var presenter: SplashPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val ivSplashScreen1 by lazy {this.findViewById<ImageView>(R.id.ivSplashScreen1)}
        presenter = SplashPresenterImp(this)
        presenter?.checkIsLogin()
    }

    override fun onLogged() {
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() }, 3000)
    }

    override fun unLogged() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, SlideActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
