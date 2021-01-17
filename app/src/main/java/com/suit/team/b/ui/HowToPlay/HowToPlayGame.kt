package com.suit.team.b.ui.HowToPlay

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.suit.team.b.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class HowToPlayGame : AppCompatActivity() {

    private var name: String = ""

    private val vpviewpager2: ViewPager2 by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.viewpager2)
    }
    private val didots_indicator: DotsIndicator by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.dots_indicator)
    }
    private val btnnext: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnNext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play_game)

        val viewPagerAdapter = ViewPagerAdapter(this) {
            name = it
        }

        vpviewpager2.adapter = viewPagerAdapter
        didots_indicator.setViewPager2(vpviewpager2)

        btnnext.setOnClickListener {
            if (vpviewpager2.currentItem < 6) {
                vpviewpager2.currentItem = vpviewpager2.currentItem.plus(1)
            } else if (name != "") {
                btnnext.text = "Finish"
                finish()
            }
        }
    }
}
