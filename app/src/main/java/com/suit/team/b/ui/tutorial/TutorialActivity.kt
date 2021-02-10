package com.suit.team.b.ui.tutorial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.suit.team.b.R
import com.suit.team.b.ui.menu_about.MenuAboutActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class TutorialActivity : AppCompatActivity() {

    private var name: String = ""

    private val viewPager2: ViewPager2 by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.viewPager2)
    }
    private val dotsIndicator: DotsIndicator by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.dotsIndicator)
    }
    private val btnNext: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnNext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val viewPagerAdapter = ViewPagerAdapter(this) { name = it }

        viewPager2.adapter = viewPagerAdapter
        dotsIndicator.setViewPager2(viewPager2)

        btnNext.setOnClickListener {
            if (viewPager2.currentItem < 1) {
                viewPager2.currentItem = viewPager2.currentItem.plus(1)
            } else {
                startActivity(Intent(this, MenuAboutActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MenuAboutActivity::class.java))
        finish()
    }
}
