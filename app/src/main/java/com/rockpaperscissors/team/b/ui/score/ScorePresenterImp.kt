package com.rockpaperscissors.team.b.ui.score

import com.rockpaperscissors.team.b.data.local.SharedPref

class ScorePresenterImp(private val view: ScoreView) : ScorePresenter {

    override fun getRankVsP() {
        val dataScoreVsP = SharedPref.getRankedScoreVsP()
        view.onSuccess(dataScoreVsP)
    }

    override fun getRankVsCPU() {
        val dataScoreVsCom = SharedPref.getRankedScoreVsCPU()
        view.onSuccess(dataScoreVsCom)
    }

}