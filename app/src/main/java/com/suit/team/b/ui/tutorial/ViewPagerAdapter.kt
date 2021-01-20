package com.suit.team.b.ui.tutorial

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suit.team.b.R

class ViewPagerAdapter(
    fa: FragmentActivity,
    listener: (String) -> Unit,
) : FragmentStateAdapter(fa) {
    private val context = fa as Context
    private val dataFragments = mutableListOf(
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvp),
            R.drawable.ic_pvplayer,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvp_one),
            R.drawable.ic_menupvp,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvp_two),
            R.drawable.ic_menupvp2,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvp_three),
            R.drawable.ic_menupvp3,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvc),
            R.drawable.ic_pvcomputer,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvc_one),
            R.drawable.ic_pvcom1,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvc_two),
            R.drawable.ic_pvcom2,
            listener
        ),
    )

    override fun getItemCount(): Int = dataFragments.size
    override fun createFragment(position: Int): Fragment = dataFragments[position]
}
