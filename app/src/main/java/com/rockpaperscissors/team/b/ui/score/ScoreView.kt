package com.rockpaperscissors.team.b.ui.score

import com.rockpaperscissors.team.b.data.model.Score

interface ScoreView {
    fun onSuccess(dataScore: MutableList<Score>)
}