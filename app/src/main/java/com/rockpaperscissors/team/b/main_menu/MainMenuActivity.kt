package com.rockpaperscissors.team.b.main_menu

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rockpaperscissors.team.b.R

class MainMenuActivity : AppCompatActivity() {
    private val ivPlayerVsPlayer: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_player_vs_player)
    }

    private val ivPlayerVsKomputer: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_player_vs_komputer)
    }

    private val ivSkor: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_skor)
    }

    private val ivPengaturan: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_pengaturan)
    }

    private val ivTentang: ImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.iv_tentang)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        ivPlayerVsPlayer.setOnClickListener {

        }

        ivPlayerVsKomputer.setOnClickListener {

        }

        ivSkor.setOnClickListener {

        }

        ivPengaturan.setOnClickListener {

        }

        ivTentang.setOnClickListener {

        }
    }
}
