package com.suit.team.b.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.Score
import com.suit.team.b.utils.GameType


class ScoreFragment(private val gameType: GameType) : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var viewModel: ScoreViewModel? = null
    private var score: MutableList<Score>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.score_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)

        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        recyclerView?.adapter = ScoreRVAdapter(score)

        if (gameType == GameType.Multiplayer) {
            viewModel?.fetchScoreMulti()
            viewModel?.scoreMulti?.observe(viewLifecycleOwner) {
                recyclerView?.adapter = ScoreRVAdapter(it)
            }
        } else {
            viewModel?.fetchScoreSingle()
            viewModel?.scoreSingle?.observe(viewLifecycleOwner) {
                recyclerView?.adapter = ScoreRVAdapter(it)
            }
        }

        viewModel?.errorRegister?.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
