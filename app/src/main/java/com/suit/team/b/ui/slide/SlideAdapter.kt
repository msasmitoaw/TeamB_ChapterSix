package com.suit.team.b.ui.slide

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SlideAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {
    private val dataFragments = mutableListOf(
            FirstSegment.newInstance("Bermain suit melawan sesama pemain"),
            FirstSegment.newInstance("Bermain suit melawan komputer"),
            FirstSegment.newInstance("Masuk Permainan")
    )

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment = dataFragments[position]
}
