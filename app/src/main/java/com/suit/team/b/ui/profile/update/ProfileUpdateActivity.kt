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
import com.suit.team.b.data.model.Users
import com.suit.team.b.ui.auth.AuthActivity
import com.suit.team.b.utils.EmailValidator
import com.suit.team.b.utils.text

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
            val name = etName?.text()
            val username = etUsername?.text()
            val email = etEmail?.text()

            if (name?.isNullOrBlank() == true ||
                username?.isNullOrBlank() == true ||
                email?.isNullOrBlank() == true
            ) {
                Toast.makeText(this, getString(R.string.blank_input), Toast.LENGTH_SHORT).show()
            } else if (!etEmail?.error.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    getString(R.string.email_format_validation),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                presenter?.checkDuplicate(username, email)!!
            }
        }

        etEmail?.addTextChangedListener(EmailValidator(etEmail!!))
    }

    override fun onStart() {
        super.onStart()
        presenter?.fetchUser()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNoDuplicate() {
        val updateDialog = UpdateDialogFragment(this)
        updateDialog.show(supportFragmentManager, null)
    }

    override fun onFetchSuccess(user: Users) {
        etName?.setText(user.name, TextView.BufferType.EDITABLE)
        etEmail?.setText(user.email, TextView.BufferType.EDITABLE)
        etUsername?.setText(user.username, TextView.BufferType.EDITABLE)
    }

    override fun onUpdateSuccess() {
        Toast.makeText(this, getString(R.string.update_success), Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun onFailed(toastString: String) {
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show()
    }

    override fun onPasswordEntered(pass: String) {
        val name = etName?.text.toString()
        val email = etEmail?.text.toString()
        val username = etUsername?.text.toString()
        presenter?.verifyAndUpdate(name, username, email, pass)
    }
}
