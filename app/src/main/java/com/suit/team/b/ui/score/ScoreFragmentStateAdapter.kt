package com.suit.team.b.ui.score

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suit.team.b.utils.GameType

class ScoreFragmentStateAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    private val dataFragment = mutableListOf(
        ScoreFragment(GameType.Multiplayer),
        ScoreFragment(GameType.Singleplayer),
        HistoryFragment()
    )

    override fun getItemCount(): Int = dataFragment.size

    override fun createFragment(pos: Int): Fragment = dataFragment[pos]
}
