package com.suit.team.b.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.ui.MenuAbout.MainMenuAbout
import com.suit.team.b.ui.game.GameActivity
import com.suit.team.b.ui.score.ScoreActivity
import com.suit.team.b.utils.string

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mutableListOf(
                R.id.iv_player_vs_player,
                R.id.iv_player_vs_computer,
                R.id.iv_score,
                R.id.iv_setting,
                R.id.iv_about,
        ).forEachIndexed { index, i ->
            findViewById<ImageView>(i).setOnClickListener {
                when (index) {
                    0 -> {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("mode", string(R.string.player_two))
                        startActivity(intent)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("mode", string(R.string.cpu))
                        startActivity(intent)
                        finish()
                    }
                    2 -> {
                        startActivity(Intent(this, ScoreActivity::class.java))
                    }
                    3 -> {}
                    4 -> startActivity(Intent(this, MainMenuAbout::class.java))
                    else -> finish()
                }
            }
        }
    }
}
