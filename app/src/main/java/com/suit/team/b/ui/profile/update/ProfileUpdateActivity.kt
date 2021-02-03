package com.suit.team.b.ui.profile.update

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.suit.team.b.R
import com.suit.team.b.utils.string
import com.suit.team.b.utils.text
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File

class ProfileUpdateActivity : AppCompatActivity() {

    private lateinit var viewModel: UpdateViewModel
    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var btSave: Button
    private lateinit var btCancel: Button
    private lateinit var ivPhoto: ImageView
    private var filePath: File? = null


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profileupdate_activity)

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        etUsername = findViewById(R.id.etUsername)
        tilUsername = findViewById(R.id.tilUsername)
        etEmail = findViewById(R.id.etEmail)
        tilEmail = findViewById(R.id.tilEmail)

        btSave = findViewById(R.id.btSave)
        btCancel = findViewById(R.id.btCancel)
        ivPhoto = findViewById(R.id.ivPhoto)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_update_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        viewModel.fetchUserData()

        viewModel.resultRegister.observe(this) {
            Glide.with(this).load(it.data?.photo).into(ivPhoto)
            etUsername.setText(it.data?.username, TextView.BufferType.EDITABLE)
            etEmail.setText(it.data?.email, TextView.BufferType.EDITABLE)
            Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show()
        }

        viewModel.errorRegister.observe(this) {
            when (it) {
                R.string.username_exist.toString() -> tilUsername.error =
                    string(R.string.username_exist)
                R.string.email_exist.toString() -> tilEmail.error = string(R.string.email_exist)
                R.string.username_not_alphanumeric.toString() -> tilUsername.error =
                    string(R.string.username_not_alphanumeric)
                R.string.username_shorter.toString() -> tilUsername.error =
                    string(R.string.username_shorter)
                else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        btSave.setOnClickListener {
            tilEmail.error = null
            tilUsername.error = null

            if (etUsername.text?.isBlank() == true) {
                tilUsername.error = string(R.string.username_validation)
                return@setOnClickListener
            }

            if (etEmail.text?.isBlank() == true) {
                tilEmail.error = string(R.string.email_validation)
                return@setOnClickListener
            }
            if (etUsername.text().length < 6) {
                tilUsername.error = string(R.string.username_shorter)
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text()).matches()) {
                tilEmail.error =
                    string(R.string.email_format_validation)
                return@setOnClickListener
            }

            if (filePath == null) {
                viewModel.putUser(etUsername.text(), etEmail.text())
            } else {
                filePath?.let { viewModel.putUser(etUsername.text(), etEmail.text(), it) }
            }
        }

        btCancel.setOnClickListener { super.onBackPressed() }

        ivPhoto.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                filePath = resultUri.toFile()
                Glide.with(this).load(filePath).into(ivPhoto)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                viewModel.errorRegister.value = this.getString(R.string.Capture_image_failed)
                result.error.printStackTrace()
            }
        }
    }
}
