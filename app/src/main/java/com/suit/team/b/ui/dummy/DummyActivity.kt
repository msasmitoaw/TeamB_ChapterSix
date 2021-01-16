package com.suit.team.b.ui.dummy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Score
import com.suit.team.b.ui.score.ScoreActivity
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.PlayerType

class DummyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dummy_activity)


        val submit = findViewById<Button>(R.id.btSubmit)
        val show = findViewById<Button>(R.id.btShow)
        val scoreShow = findViewById<Button>(R.id.btScoreShow)
        val etPlayer = findViewById<EditText>(R.id.etPlayer)
        val etScore = findViewById<EditText>(R.id.etScore)
        val etGameType = findViewById<EditText>(R.id.etGameType)
        val tvP1VP2 = findViewById<TextView>(R.id.tvP1VP2)
        val tvP1VCPU = findViewById<TextView>(R.id.tvP1VCPU)
        val tvP2 = findViewById<TextView>(R.id.tvP2)
        val tvCPU = findViewById<TextView>(R.id.tvCPU)
        val tvScoreP1VP2 = findViewById<TextView>(R.id.tvScoreP1VP2)
        val tvScoreP1VCPU = findViewById<TextView>(R.id.tvScoreP1VCPU)
        val tvScoreP2 = findViewById<TextView>(R.id.tvScoreP2)
        val tvScoreCPU = findViewById<TextView>(R.id.tvScoreCPU)

        show.setOnClickListener {
            tvP1VP2.text = "Nilai User VS P2"
            tvScoreP1VP2.text = SharedPref.getScore(PlayerType.P1, GameType.VSP)

            tvP1VCPU.text = "Nilai User VS CPU"
            tvScoreP1VCPU.text = SharedPref.getScore(PlayerType.P1, GameType.VSCPU)

            tvP2.text = "Nilai P2 VS USER"
            tvScoreP2.text = SharedPref.getScore(PlayerType.P2)
            tvCPU.text = "Nilai CPU VS USER"
            tvScoreCPU.text = SharedPref.getScore(PlayerType.CPU)
        }

        scoreShow.setOnClickListener {
            startActivity(Intent(this,ScoreActivity::class.java))
        }

        submit.setOnClickListener {
            when (etPlayer.text.toString()) {
                PlayerType.P1.toString()
                -> {
                    if (etGameType.text.toString() == GameType.VSP.toString())
                        SharedPref.scoreToPref(
                            Score(
                                playerType = PlayerType.P1,
                                scoreValue = etScore.text.toString().toInt(),
                                gameType = GameType.VSP
                            )
                        ) else {
                        Score(
                            playerType = PlayerType.P1,
                            scoreValue = etScore.text.toString().toInt(),
                            gameType = GameType.VSCPU
                        )
                    }
                }

                PlayerType.P2.toString()
                -> {
                    SharedPref.scoreToPref(
                        Score(
                            playerType = PlayerType.P2,
                            scoreValue = etScore.text.toString().toInt()
                        )
                    )
                }

                PlayerType.CPU.toString()
                -> {
                    SharedPref.scoreToPref(
                        Score(
                            playerType = PlayerType.CPU,
                            scoreValue = etScore.text.toString().toInt()
                        )
                    )
                }
            }
        }
    }
}
