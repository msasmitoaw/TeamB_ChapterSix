package com.rockpaperscissors.team.b.ui.gameplay

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.rockpaperscissors.team.b.R
import com.rockpaperscissors.team.b.data.model.Player
import com.rockpaperscissors.team.b.utils.onSelected
import com.rockpaperscissors.team.b.utils.setWord
import com.rockpaperscissors.team.b.utils.string
import java.util.*

class GamePlayActivity : AppCompatActivity(), GamePlayView {
    private var presenter: GamePlayPresenter? = null

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
        onPlayerOnePick()
    }

    override fun onPlayerOnePick() {
        mutableListOf(
            R.id.btnRockOne, R.id.btnScissorsOne, R.id.btnPaperOne
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                val playerOne = string(R.string.player_one)
                val player = Player(btn.tag.toString().toLowerCase(Locale.ROOT))
                setWord(this, "$playerOne ${string(R.string.choose)} ${btn.tag}")
                Log.d("MainActivity", btn.tag.toString())
                btn.onSelected(this)
                presenter?.setPlayer(player)
                onPlayerTwoPick()
            }
        }
    }

    override fun onPlayerTwoPick() {
        mutableListOf(
            R.id.btnRockTwo, R.id.btnScissorsTwo, R.id.btnPaperTwo
        ).forEachIndexed { _, i ->
            findViewById<ImageButton>(i).setOnClickListener {
                val btn = it as ImageButton
                val playerTwo = string(R.string.player_two)
                val player = Player(btn.tag.toString().toLowerCase(Locale.ROOT))
                setWord(this, "$playerTwo ${string(R.string.choose)} ${btn.tag}")
                Log.d("MainActivity", btn.tag.toString())
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
        setWord(this, result)
        Log.d("MainActivity", result)
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}
