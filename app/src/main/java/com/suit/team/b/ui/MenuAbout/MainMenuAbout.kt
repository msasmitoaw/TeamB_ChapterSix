package com.suit.team.b.ui.MenuAbout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.suit.team.b.R
import com.suit.team.b.ui.HowToPlay.HowToPlayGame
import com.suit.team.b.ui.MenuAbout.about.ViewAbout

class MainMenuAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_about)

        ivtutorial.setOnClickListener {
            val intent = Intent(this@MainMenuAbout, HowToPlayGame::class.java)
            startActivity(intent)
        }
        ivabout.setOnClickListener {
            val intent = Intent(this@MainMenuAbout, ViewAbout::class.java)
            startActivity(intent)
        }
        btnback.setOnClickListener {
            finish()
        }
    }

    private val ivtutorial: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.ivTutorial)
    }
    private val ivabout: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.ivAbout)
    }
    private val btnback: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnBackAbout)
    }
}
