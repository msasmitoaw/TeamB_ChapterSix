package com.suit.team.b.ui.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.ui.auth.AuthViewModel
import com.suit.team.b.ui.score.ScoreActivity
import kotlin.math.abs

private const val MIN_SCALE = 0.75f

class HistoryActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)

        val factory = HistoryViewModel.Factory(ApiModule.service, SharedPref)
        val viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.history_games)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        val hisAdapter = HistoryStateFragment(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.vpHistoryScore)

        viewPager2.adapter = hisAdapter
        viewPager2.setPageTransformer(ScoreActivity.FlipHorizontalPageTransformer())

        val tabLayout = findViewById<TabLayout>(R.id.tabHistoryLayout)
        val tabTitle = arrayOf(R.string.history, R.string.bookmark)
        val tabIcon = arrayOf(R.drawable.ic_history_games, R.drawable.ic_bookmark)

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