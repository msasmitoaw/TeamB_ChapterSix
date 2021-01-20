package com.suit.team.b.ui.slide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.suit.team.b.R
import com.suit.team.b.ui.auth.AuthenticationActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class SlideActivity : AppCompatActivity() {
    private var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)
        val viewpager2 by lazy {this.findViewById<ViewPager2>(R.id.viewpager2)}
        val dotsIndicator by lazy {this.findViewById<DotsIndicator>(R.id.dots_indicator)}
        val btnNext by lazy { this.findViewById<Button>(R.id.btnNext) }
        val viewPagerAdapter = SlideAdapter(this) {
            name = it.toString()
        }
        viewpager2.adapter = viewPagerAdapter

        dotsIndicator.setViewPager2(viewpager2)

        btnNext.setOnClickListener {
            if(viewpager2.currentItem < 2){
                viewpager2.currentItem = viewpager2.currentItem.plus(1)
            }
            else{
                startActivity(Intent(this, AuthenticationActivity::class.java))
                finish()
            }
        }
    }
}