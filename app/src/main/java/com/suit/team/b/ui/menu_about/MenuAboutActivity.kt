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

        ivtutorial.setOnClickListener {
            val intent = Intent(this@MenuAboutActivity, TutorialActivity::class.java)
            startActivity(intent)
        }
        ivabout.setOnClickListener {
            val intent = Intent(this@MenuAboutActivity, AboutActivity::class.java)
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
