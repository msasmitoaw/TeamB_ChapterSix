package com.suit.team.b.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.auth.AuthActivity
import com.suit.team.b.ui.game.GameActivity
import com.suit.team.b.ui.history.HistoryActivity
import com.suit.team.b.ui.menu_about.MenuAboutActivity
import com.suit.team.b.ui.profile.show.ProfilePageActivity
import com.suit.team.b.ui.score.ScoreActivity
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.SoundBackground

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val factory = MainViewModel.Factory(ApiModule.service)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        startService(Intent(this, SoundBackground::class.java))
        mutableListOf(
            R.id.ivPlayerVsPlayer,
            R.id.ivPlayerVsComputer,
            R.id.ivScore,
            R.id.ivHistory,
            R.id.ivSetting,
            R.id.ivAbout2,
        ).forEachIndexed { index, i ->
            findViewById<ImageView>(i).setOnClickListener {
                when (index) {
                    0 -> {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("mode", GameType.Multiplayer)
                        startActivity(intent)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("mode", GameType.Singleplayer)
                        startActivity(intent)
                        finish()
                    }
                    2 -> startActivity(Intent(this, ScoreActivity::class.java))
                    3 -> startActivity(Intent(this, HistoryActivity::class.java))
                    4 -> {
                        startActivity(Intent(this, ProfilePageActivity::class.java))
                        finish()
                    }
                    5 -> startActivity(Intent(this, MenuAboutActivity::class.java))
                    else -> finish()
                }
                stopSound()
            }
        }

        viewModel.startRenewToken()
        viewModel.onErrorResponse().observe(this, {
            startActivity(Intent(this, AuthActivity::class.java))
            viewModel.logout()
            finish()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopSound()
    }

    private fun stopSound() {
        stopService(Intent(this, SoundBackground::class.java))
    }
}
