package com.suit.team.b.ui.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suit.team.b.R

class ViewPagerAdapter(
    fa: FragmentActivity,
    listener: (String) -> Unit
) : FragmentStateAdapter(fa) {
    private val dataFragments = mutableListOf(
        SecondFragment.newInstance(
            "Tutorial permainan player vs player",
            R.drawable.ic_pvplayer,
            listener
        ),
        SecondFragment.newInstance(
            "Player pertama memilih terlebih dahulu",
            R.drawable.ic_menupvp,
            listener
        ),
        SecondFragment.newInstance(
            "Kemudian player dua memilih pilihannya",
            R.drawable.ic_menupvp2,
            listener
        ),
        SecondFragment.newInstance(
            "Menampilkan hasil siapa yg menang dan menanyakan main lagi atau kembali ke menu",
            R.drawable.ic_menupvp3,
            listener
        ),
        SecondFragment.newInstance(
            "Tutorial permainan player vs computer",
            R.drawable.ic_pvcomputer,
            listener
        ),
        SecondFragment.newInstance(
            "Player pertama memillih terlebih dahulu kemudian komputer merandom pilihannya ",
            R.drawable.ic_pvcom1,
            listener
        ),
        SecondFragment.newInstance(
            "Menampilkan hasil siapa yg menang dan menanyakan main lagi atau kembali ke menu",
            R.drawable.ic_pvcom2,
            listener
        ),
    )

    override fun getItemCount(): Int = dataFragments.size
    override fun createFragment(position: Int): Fragment = dataFragments[position]
}
