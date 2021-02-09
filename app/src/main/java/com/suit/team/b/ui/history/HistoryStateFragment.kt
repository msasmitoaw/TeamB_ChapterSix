package com.suit.team.b.ui.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.ScoreType

class HistoryStateFragment(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {
    private val dataFragment = mutableListOf(
        HistoryFragment(ScoreType.HISTORY),
        HistoryFragment(ScoreType.BOOKMARK)
    )

    override fun getItemCount(): Int = dataFragment.size

    override fun createFragment(pos: Int): Fragment = dataFragment[pos]
}