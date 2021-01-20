package com.suit.team.b.ui.menu_about

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.ui.about.AboutActivity
import com.suit.team.b.ui.tutorial.TutorialActivity

class MenuAboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_about)

        ivTutorial.setOnClickListener {
            startActivity(Intent(this@MenuAboutActivity, TutorialActivity::class.java))
            finish()
        }
        ivAbout.setOnClickListener {
            startActivity(Intent(this@MenuAboutActivity, AboutActivity::class.java))
            finish()
        }
        btnBack.setOnClickListener {
            finish()
        }
    }

    private val ivTutorial: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.ivTutorial)
    }
    private val ivAbout: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.ivAbout)
    }
    private val btnBack: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnBackAbout)
    }
}
