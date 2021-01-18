package com.suit.team.b.ui.MenuAbout.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.suit.team.b.R

class ViewAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_about)
        tvabout.text = getString(R.string.content_about)

        btncontent.setOnClickListener {
            finish()
        }

    }


    private val tvabout: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.textabout)
    }
    private val btncontent: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnbcontent)
    }
}
