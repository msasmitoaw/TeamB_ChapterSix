package com.suit.team.b.ui.score

import android.annotation.SuppressLint
import android.content.Intent
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
import com.suit.team.b.ui.main.MainActivity
import kotlin.math.abs

private const val MIN_SCALE = 0.75f

class ScoreActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.score_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        val vp2adapter = ScoreFragmentStateAdapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.vpScore)
        viewPager2.adapter = vp2adapter
        viewPager2.setPageTransformer(FlipHorizontalPageTransformer())

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tabTitle = arrayOf(R.string.vs_Player, R.string.vs_CPU, R.string.history)
        val tabIcon = arrayOf(
            R.drawable.ic_tabscore_vp,
            R.drawable.ic_tabscore_vcpu,
            R.drawable.ic_tabscore_history
        )

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.text = resources.getString(tabTitle[pos])
            tab.setIcon(tabIcon[pos])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackAction()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = onBackAction()

    private fun onBackAction() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    class FlipHorizontalPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <= 0 -> {
                        alpha = 1f
                        translationX = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> {
                        alpha = 1 - position

                        translationX = pageWidth * -position

                        val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position)))
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> {
                        alpha = 0f
                    }
                }
            }
        }
    }
}
