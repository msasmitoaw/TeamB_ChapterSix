package com.rockpaperscissors.team.b.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rockpaperscissors.team.b.R
import com.rockpaperscissors.team.b.data.model.Score
import com.rockpaperscissors.team.b.utils.GameType


class ScoreFragment(private val gameType: GameType) : Fragment(), ScoreView {

    private var recyclerView : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.score_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = ScorePresenterImp(this)
        when (gameType) {
            GameType.VSP -> presenter.getDataRankVsP()
            GameType.VSCPU -> presenter.getDataRankVsCPU()
        }
    }

    override fun onSuccess(dataScore: MutableList<Score>) {
        val adapter = ScoreRVAdapter(dataScore)
        recyclerView = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        recyclerView?.adapter = adapter
    }

}