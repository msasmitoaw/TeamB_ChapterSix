package com.suit.team.b.ui.score

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suit.team.b.R

class ScoreActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.score_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.window_background))))

        val vp2adapter = ScoreFragmentStateAdapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.vpScore)
        viewPager2.adapter = vp2adapter
        viewPager2.setPageTransformer(FlipHorizontalPageTransformer())


        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tabTitle = arrayOf(R.string.vs_Player, R.string.vs_CPU)
        val tabIcon = arrayOf(R.drawable.ic_tabscore_vp, R.drawable.ic_tabscore_vcpu)

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.text = resources.getString(tabTitle[pos])
            tab.setIcon(tabIcon[pos])
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class FlipHorizontalPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, pos: Float) {
            val rotation = (180f * pos)
            val alpha = if (rotation > 90f || rotation < -90f) 0f else 1f

            page.alpha = alpha
            page.pivotX = page.width * 0.5f
            page.pivotY = page.height * 0.5f
            page.rotationY = rotation
        }

    }
}
