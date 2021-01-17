package com.suit.team.b.ui.profile.show

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.suit.team.b.R
import com.suit.team.b.data.model.User
import com.suit.team.b.ui.profile.update.ProfileUpdateActivity

class ProfilePageActivity : AppCompatActivity(), ProfileView {

    private var presenter: ProfilePresenter? = null
    private var tvName: TextView? = null
    private var tvUsername: TextView? = null
    private var tvEmail: TextView? = null
    private var btUpdate: Button? = null
    private var btLogout: Button? = null
    private var btDelete: Button? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profilepage_activity)

        presenter = ProfilePresenterImp(this)

        tvName = findViewById(R.id.etName)
        tvUsername = findViewById(R.id.etUsername)
        tvEmail = findViewById(R.id.etEmail)
        btUpdate = findViewById(R.id.btUpdate)
        btLogout = findViewById(R.id.btLogout)
        btDelete = findViewById(R.id.btDelete)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        btUpdate?.setOnClickListener {
            startActivity(Intent(this, ProfileUpdateActivity::class.java))
        }

        btLogout?.setOnClickListener {
            presenter?.logout()
            this.startActivity(
                Intent(this, ProfileUpdateActivity::class.java)
            )
            finish()
        }

        btDelete?.setOnClickListener {
            val deleteDialog = DialogFragmentDelete(this)
            deleteDialog.show(supportFragmentManager, null)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.showProfile()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onShowSuccess(user: User) {
        tvName?.text = user.name
        tvEmail?.text = user.email
        tvUsername?.text = user.username
    }

    override fun onShowFailed(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteSuccess(toastString: String) {
        this.startActivity(
            Intent(this, ProfileUpdateActivity::class.java),
            bundleOf(getString(R.string.del_message) to toastString)
        )
        finish()
    }

    override fun onDeleteFailed(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }
}
