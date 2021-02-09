package com.suit.team.b.ui.score

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suit.team.b.R
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.getString
import com.suit.team.b.utils.string

class ScoreFragmentStateAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    private val dataFragment = mutableListOf(
        ScoreFragment(GameType.Multiplayer),
        ScoreFragment(GameType.Singleplayer),
        HistoryFragment(fa.baseContext.string(R.string.history)),
        HistoryFragment(fa.baseContext.string(R.string.bookmark)),
    )

    override fun getItemCount(): Int = dataFragment.size

    override fun createFragment(pos: Int): Fragment = dataFragment[pos]
}
