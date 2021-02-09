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
            R.raw.turialpvsp,
            listener
        ),
        TutorialFragment.newInstance(
            context.getString(R.string.tutorial_pvc),
            R.raw.tutorialpvcom,
            listener
        )
    )

    override fun getItemCount(): Int = dataFragments.size
    override fun createFragment(position: Int): Fragment = dataFragments[position]
}
