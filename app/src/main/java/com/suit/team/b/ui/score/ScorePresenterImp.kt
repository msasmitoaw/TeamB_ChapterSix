package com.suit.team.b.ui.score

import com.suit.team.b.data.local.SharedPref

class ScorePresenterImp(private val view: ScoreView) : ScorePresenter {

    override fun getDataRankVsP() {
        val dataScoreVsP = SharedPref.getDataScoreVsP()
        dataScoreVsP.sortByDescending { it.scoreValue }
        view.onSuccess(dataScoreVsP)
    }

    override fun getDataRankVsCPU() {
        val dataScoreVsCom = SharedPref.getDataScoreVsCPU()
        dataScoreVsCom.sortByDescending { it.scoreValue }
        view.onSuccess(dataScoreVsCom)
    }

}
