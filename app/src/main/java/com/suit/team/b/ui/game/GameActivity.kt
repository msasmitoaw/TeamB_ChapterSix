package com.suit.team.b.ui.game

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.R.string.*
import com.suit.team.b.data.model.Player
import com.suit.team.b.ui.main.MainActivity
import com.suit.team.b.utils.onSelected
import com.suit.team.b.utils.setWord
import com.suit.team.b.utils.string

class GameActivity : AppCompatActivity(), GameView {
    private lateinit var presenter: GamePresenter
    private lateinit var playerOne: String
    private lateinit var playerTwo: String

    private val llPlayerOne: LinearLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.llPlayerOne) }
    private val llPlayerTwo: LinearLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.llPlayerTwo) }
    private val tvPlayerOne: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tvPlayerOne) }
    private val tvPlayerTwo: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.tvPlayerTwo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        presenter = GamePresenterImp(this)
        mutableListOf(R.id.btnHome, R.id.btnClose).forEachIndexed { index, i ->
            findViewById<Button>(i).setOnClickListener { if (index == 1) finish() else backToMenu() }
        }
        playerOne = string(player_one)
        playerTwo = intent.getStringExtra("mode").toString()
        tvPlayerOne.text = playerOne
        tvPlayerTwo.text = playerTwo
        onPlayerOnePick()
    }

    private fun backToMenu() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onPlayerOnePick() {
        llPlayerTwo.visibility = View.GONE
        mutableListOf(
            R.id.btnRockOne, R.id.btnScissorsOne, R.id.btnPaperOne
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                val player = Player(btn.tag.toString())
                setWord(this, "$playerOne ${string(choose)} ${btn.tag}")
                btn.onSelected(this)
                presenter.setPlayer(player)
                if (playerTwo == string(cpu)) onComputerPick() else onPlayerTwoPick()
            }
        }
    }

    override fun onPlayerTwoPick() {
        llPlayerOne.visibility = View.GONE
        llPlayerTwo.visibility = View.VISIBLE
        mutableListOf(
            R.id.btnRockTwo, R.id.btnScissorsTwo, R.id.btnPaperTwo
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                val player = Player(btn.tag.toString())
                setWord(this, "$playerTwo ${string(choose)} ${btn.tag}")
                btn.onSelected(this)
                presenter.setPlayerTwo(player)
                presenter.result()
            }
        }
    }

    override fun onComputerPick() {
        llPlayerOne.visibility = View.GONE
        llPlayerTwo.visibility = View.VISIBLE
        playerTwo = intent.getStringExtra("mode").toString()
        presenter.setPlayerTwo()
        val playerTwoBet = presenter.getPlayerTwo().bet
        setWord(this, "$playerTwo ${string(choose)} $playerTwoBet")
        mutableListOf(
            R.id.btnRockTwo, R.id.btnScissorsTwo, R.id.btnPaperTwo
        ).forEachIndexed { _, i ->
            val btn = findViewById<ImageButton>(i)
            if (btn.tag == playerTwoBet) btn.onSelected(this)
        }
        presenter.result()
    }

    override fun onResult(result: String) {
        llPlayerOne.visibility = View.VISIBLE

        val gameEnd = when (result) {
            string(player_one_win) -> string(player_one_win)
            string(player_two_win) -> if (playerTwo == string(cpu)) string(cpu_win)
            else string(player_two_win)
            else -> result
        }
        setWord(this, gameEnd)

        val view = LayoutInflater.from(this)
            .inflate(R.layout.custom_dialog, null, false)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(view)
        var dialog = dialogBuilder.create()
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

    override fun onRefresh() {
        mutableListOf(
            R.id.btnRockOne, R.id.btnRockTwo, R.id.btnPaperOne,
            R.id.btnPaperTwo, R.id.btnScissorsOne, R.id.btnScissorsTwo,
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).background = null
        }
    }
}
