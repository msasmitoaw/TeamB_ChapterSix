package com.suit.team.b.ui.game

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.R.string.*
import com.suit.team.b.data.model.Player
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.databinding.ActivityGameBinding
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.onSelected
import com.suit.team.b.utils.setWord
import com.suit.team.b.utils.string

class GameActivity : AppCompatActivity() {
    private lateinit var playerOneName: String
    private lateinit var playerTwoName: String
    private lateinit var gameMode: GameType
    private lateinit var bind: ActivityGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val factory = GameViewModel.Factory(ApiModule.service)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]

        bind.btnHome.setOnClickListener { backToMenu() }

        gameMode = intent.getSerializableExtra("mode") as GameType
        playerOneName = intent.getStringExtra("username").toString()
        playerTwoName = if (gameMode == GameType.Multiplayer) string(player_two) else string(CPU)
        bind.tvPlayerOne.text = playerOneName
        bind.tvPlayerTwo.text = playerTwoName
        onPlayerOnePick()
    }

    override fun onBackPressed() = backToMenu()

    private fun backToMenu() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onPlayerOnePick() {
        bind.llPlayerTwo.visibility = View.GONE
        mutableListOf(
            bind.btnRockOne, bind.btnScissorsOne, bind.btnPaperOne
        ).forEachIndexed { _, i ->
            i.setOnClickListener {
                setWord(this, "$playerOneName ${string(choose)} ${it.tag}")
                it.onSelected(this)
                val bet = getStringResId(it.tag.toString())
                viewModel.setPlayer(Player(bet))
                if (playerTwoName == string(cpu)) onComputerPick() else onPlayerTwoPick()
            }
        }
    }

    private fun getStringResId(tag: String): Int = when (tag) {
        string(scissors_caps) -> scissors_caps
        string(paper_caps) -> paper_caps
        else -> rock_caps
    }

    private fun onPlayerTwoPick() {
        bind.llPlayerOne.visibility = View.GONE
        bind.llPlayerTwo.visibility = View.VISIBLE
        mutableListOf(
            bind.btnRockTwo, bind.btnScissorsTwo, bind.btnPaperTwo
        ).forEachIndexed { _, i ->
            i.setOnClickListener {
                setWord(this, "$playerTwoName ${string(choose)} ${it.tag}")
                it.onSelected(this)
                val bet = getStringResId(it.tag.toString())
                viewModel.setPlayerTwo(Player(bet))
                onResult(string(viewModel.result(gameMode)))
            }
        }
    }

    private fun onComputerPick() {
        bind.llPlayerOne.visibility = View.GONE
        bind.llPlayerTwo.visibility = View.VISIBLE
        viewModel.setPlayerTwo()
        val playerTwoBet = string(viewModel.getPlayerTwo().bet)
        setWord(this, "$playerTwoName ${string(choose)} $playerTwoBet")
        mutableListOf(
            bind.btnRockTwo, bind.btnScissorsTwo, bind.btnPaperTwo
        ).forEachIndexed { _, i -> if (i.tag == playerTwoBet) i.onSelected(this) }
        onResult(string(viewModel.result(gameMode)))
    }

    private fun onResult(result: String) {
        viewModel.onErrorResponse().observe(this, {
            setWord(this, it)
        })

        bind.llPlayerOne.visibility = View.VISIBLE

        val gameEnd = when (result) {
            string(player_one_win) -> {
                var count = bind.tvPlayerOneScore.text.toString().replace("#", "").toInt()
                count++
                val score = string(_tags) + count
                bind.tvPlayerOneScore.text = score
                "$playerOneName${string(wins)}"
            }
            string(player_two_win) -> {
                var count = bind.tvPlayerTwoScore.text.toString().replace("#", "").toInt()
                count++
                val score = string(_tags) + count
                bind.tvPlayerTwoScore.text = score
                if (playerTwoName == string(cpu)) string(cpu_win)
                else string(player_two_win)
            }
            else -> result
        }
        setWord(this, gameEnd)

        val view = LayoutInflater.from(this)
            .inflate(R.layout.custom_dialog, null, false)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        val tvDialogResult: TextView = view.findViewById(R.id.tvDialogResult)
        tvDialogResult.text = gameEnd

        val btnBackToMenu: Button = view.findViewById(R.id.btnBackToMenu)
        val btnReset: Button = view.findViewById(R.id.btnReset)

        btnBackToMenu.setOnClickListener { dialog.dismiss(); backToMenu() }
        btnReset.setOnClickListener { dialog.dismiss(); onRefresh() }
    }

    private fun onRefresh() {
        mutableListOf(
            bind.btnRockOne, bind.btnRockTwo, bind.btnPaperOne,
            bind.btnPaperTwo, bind.btnScissorsOne, bind.btnScissorsTwo,
        ).forEachIndexed { _, i -> i.background = null }
    }
}
