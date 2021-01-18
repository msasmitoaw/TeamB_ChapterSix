package com.suit.team.b.ui.profile.update

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.data.model.User
import com.suit.team.b.ui.profile.show.ProfilePageActivity

class ProfileUpdateActivity : AppCompatActivity(), UpdateView {

    private var presenter: UpdatePresenter? = null
    private var etName: EditText? = null
    private var etUsername: EditText? = null
    private var etEmail: EditText? = null
    private var btSave: Button? = null
    private var btCancel: Button? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profileupdate_activity)

        presenter = UpdatePresenterImp(this)

        etName = findViewById(R.id.etName)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        btSave = findViewById(R.id.btSave)
        btCancel = findViewById(R.id.btCancel)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_update_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        btCancel?.setOnClickListener {
            super.onBackPressed()
        }

        btSave?.setOnClickListener {
            if (etName?.text?.isBlank() == true ||
                etUsername?.text?.isBlank() == true ||
                etEmail?.text?.isBlank() == true
            ) {
                Toast.makeText(this, getString(R.string.blank_input), Toast.LENGTH_SHORT).show()
            } else {
                val updateDialog = UpdateDialogFragment(this)
                updateDialog.show(supportFragmentManager, null)
            }
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
        etName?.setText(user.name, TextView.BufferType.EDITABLE)
        etEmail?.setText(user.email, TextView.BufferType.EDITABLE)
        etUsername?.setText(user.username, TextView.BufferType.EDITABLE)
    }

    override fun onUpdateSuccess() {
        Toast.makeText(this, getString(R.string.update_success), Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, ProfilePageActivity::class.java))
        finish()
    }

    override fun onFailed(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }

    override fun onChangedDataReady(pass: String) {
        val name = etName?.text.toString()
        val email = etEmail?.text.toString()
        val username = etUsername?.text.toString()
        presenter?.verifyAndUpdate(name, username, email, pass)
    }
}
