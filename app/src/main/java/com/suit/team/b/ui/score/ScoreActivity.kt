package com.suit.team.b.ui.score

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suit.team.b.R

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.score_header);

        val vp2adapter = ScoreFragmentStateAdapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.vpScore)
        viewPager2.adapter = vp2adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tabTitle = arrayOf(R.string.vs_Player, R.string.vs_CPU)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = resources.getString(tabTitle[position])
        }.attach()
    }
}
