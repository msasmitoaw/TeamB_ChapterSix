package com.rockpaperscissors.team.b.ui.gameplay

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rockpaperscissors.team.b.R
import com.rockpaperscissors.team.b.data.model.Player
import com.rockpaperscissors.team.b.utils.onSelected
import com.rockpaperscissors.team.b.utils.setWord
import com.rockpaperscissors.team.b.utils.string
import java.util.*

class GamePlayActivity : AppCompatActivity(), GamePlayView {
    private var presenter: GamePlayPresenter? = null
    private lateinit var playerOne: String
    private lateinit var playerTwo: String
    private lateinit var llPlayerOne: LinearLayout
    private lateinit var llPlayerTwo: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)
        presenter = GamePlayPresenterImp(this)
        mutableListOf(R.id.btnHome, R.id.btnClose).forEachIndexed { index, i ->
            findViewById<Button>(i).setOnClickListener {
                if (index != 0) finish()
                else TODO("Back To Main Menu")
            }
        }

        llPlayerOne = findViewById(R.id.llPlayerOne)
        llPlayerTwo = findViewById(R.id.llPlayerTwo)
        onPlayerOnePick()
    }

    override fun onPlayerOnePick() {
        llPlayerTwo.visibility = View.GONE
        mutableListOf(
            R.id.btnRockOne, R.id.btnScissorsOne, R.id.btnPaperOne
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                val player = Player(btn.tag.toString().toLowerCase(Locale.ROOT))
                playerOne = string(R.string.player_one)
                setWord(this, "$playerOne ${string(R.string.choose)} ${btn.tag}")
                btn.onSelected(this)
                presenter?.setPlayer(player)
                onPlayerTwoPick()
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
                val player = Player(btn.tag.toString().toLowerCase(Locale.ROOT))
                playerTwo = string(R.string.player_two)
                setWord(this, "$playerTwo ${string(R.string.choose)} ${btn.tag}")
                btn.onSelected(this)
                presenter?.setPlayerTwo(player)
                presenter?.result()
            }
        }
    }

    override fun onComputerPick() {
        TODO("Not yet implemented")
    }

    override fun onResult(result: String) {
        llPlayerOne.visibility = View.VISIBLE
        setWord(this, result)

        val view = LayoutInflater.from(this)
            .inflate(R.layout.custom_dialog, null, false)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(view)

        val dialog = dialogBuilder.create()
        val tvDialogResult: TextView = view.findViewById(R.id.tvDialogResult)
        val btnBackToMenu: Button = view.findViewById(R.id.btnBackToMenu)
        val btnReset: Button = view.findViewById(R.id.btnReset)

        btnBackToMenu.setOnClickListener {
            finish()
        }

        btnReset.setOnClickListener {
            dialog.dismiss()
            onRefresh()
        }

        val gameEnd = when (result) {
            getString(R.string.player_one_win) -> "${getString(R.string.player_one)}\nMENANG!"
            getString(R.string.player_two_win) -> "${getString(R.string.player_two)}\nMENANG!"
            else -> result
        }
        tvDialogResult.text = gameEnd
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    override fun onRefresh() {
        mutableListOf(
            R.id.btnRockOne,
            R.id.btnRockTwo,
            R.id.btnPaperOne,
            R.id.btnPaperTwo,
            R.id.btnScissorsOne,
            R.id.btnScissorsTwo,
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).background = null
        }
    }
}
