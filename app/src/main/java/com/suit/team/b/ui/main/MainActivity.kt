package com.suit.team.b.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.ui.game.GameActivity
import com.suit.team.b.utils.string

class MainActivity : AppCompatActivity() {
    private val ivPlayerVsPlayer: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_player_vs_player)
    }

    private val ivPlayerVsComputer: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_player_vs_computer)
    }

    private val ivScore: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_score)
    }

    private val ivSetting: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_setting)
    }

    private val ivAbout: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_about)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivPlayerVsPlayer.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("mode", string(R.string.player_two))
            startActivity(intent)
            finish()
        }

        ivPlayerVsComputer.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("mode", string(R.string.cpu))
            startActivity(intent)
            finish()
        }

        ivScore.setOnClickListener {

        }

        ivSetting.setOnClickListener {

        }

        ivAbout.setOnClickListener {

        }
    }
}
