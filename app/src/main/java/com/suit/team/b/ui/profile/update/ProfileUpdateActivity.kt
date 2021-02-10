package com.suit.team.b.ui.profile.update

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mikhaellopez.circularimageview.CircularImageView
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
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
    private lateinit var civPhoto: CircularImageView
    private var filePath: File? = null
    private var firstOpen: Boolean = true


    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        civPhoto = findViewById(R.id.civPhoto)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.profile_update_header)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.app_bg))))

        viewModel.fetchUserData()

        viewModel.resultRegister.observe(this) {
            if (it.data?.photo != null)
                Glide.with(this).load(it.data.photo).into(civPhoto)
            etUsername.setText(it.data?.username, TextView.BufferType.EDITABLE)
            etEmail.setText(it.data?.email, TextView.BufferType.EDITABLE)
            if (!firstOpen) {
                Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show()
                it?.data?.email?.let { it1 -> viewModel.updateToken(it1) }
            }
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
            firstOpen = false

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

        civPhoto.setOnClickListener {
            val balloon = Balloon.Builder(this)
                .setArrowSize(6)
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowPosition(0.5f)
                .setWidth(BalloonSizeSpec.WRAP)
                .setHeight(BalloonSizeSpec.WRAP)
                .setPadding(5)
                .setCornerRadius(4f)
                .setAlpha(0.9f)
                .setText("Add picture.")
                .setTextSize(15f)
                .setTextColorResource(R.color.scorecard_yellow)
                .setTextIsHtml(true)
                .setIconDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_add_photo))
                .setBackgroundColorResource(R.color.black)
                .setOnBalloonClickListener {
                    CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this)
                }
                .setBalloonAnimation(BalloonAnimation.FADE)
                .setLifecycleOwner(this)
                .build()

            balloon.showAlignTop(civPhoto)
        }
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
                Glide.with(this).load(filePath).into(civPhoto)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                viewModel.errorRegister.value = this.getString(R.string.capture_image_failed)
                result.error.printStackTrace()
            }
        }
    }
}
