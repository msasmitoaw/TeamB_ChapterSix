package com.suit.team.b.ui.profile.show

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.ui.profile.update.ProfileUpdateActivity

class ProfilePageActivity : AppCompatActivity(), ProfileView {

    private var tvName: TextView? = null
    private var tvUsername: TextView? = null
    private var tvEmail: TextView? = null
    private var btUpdate: Button? = null
    private var btLogout: Button? = null
    private var btDelete: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profilepage_activity)

        tvName = findViewById(R.id.etName)
        tvUsername = findViewById(R.id.etUsername)
        tvEmail = findViewById(R.id.etEmail)
        btUpdate = findViewById(R.id.btUpdate)
        btLogout = findViewById(R.id.btLogout)
        btDelete = findViewById(R.id.btDelete)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_header)

        btUpdate?.setOnClickListener {
            startActivity(Intent(this, ProfileUpdateActivity::class.java))
        }

        btDelete?.setOnClickListener {
            val deleteDialog = DeleteDialogFragment(this)
            deleteDialog.show(supportFragmentManager, null)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }

    override fun onFailed(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }
}
