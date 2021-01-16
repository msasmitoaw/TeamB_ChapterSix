package com.suit.team.b.ui.score

import com.suit.team.b.data.model.Score

interface ScoreView {
    fun onSuccess(dataScore: MutableList<Score>)
}
