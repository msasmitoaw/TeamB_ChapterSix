package com.suit.team.b.ui.profile.update

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suit.team.b.R
import com.suit.team.b.ui.profile.show.ProfilePresenter
import com.suit.team.b.ui.profile.show.ProfileView

class ProfileUpdateActivity : AppCompatActivity(), ProfileView {

    private val presenter: ProfilePresenter? = null
    private var etName: EditText? = null
    private var etUsername: EditText? = null
    private var etEmail: EditText? = null
    private var btSave: Button? = null
    private var btCancel: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profileupdate_activity)

        etName = findViewById(R.id.etName)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        btSave = findViewById(R.id.btSave)
        btCancel = findViewById(R.id.btCancel)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_update_header)

        btCancel?.setOnClickListener {
            super.onBackPressed()
        }

        btSave?.setOnClickListener {
            val updateDialog = UpdateDialogFragment(this)
            updateDialog.show(supportFragmentManager,null)
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
