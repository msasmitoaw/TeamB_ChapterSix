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
        tvAbout.text = getString(R.string.content_about)

        btnContent.setOnClickListener {
            finish()
        }
    }

    private val tvAbout: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.textAbout)
    }
    private val btnContent: Button by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.btnBackContent)
    }
}
