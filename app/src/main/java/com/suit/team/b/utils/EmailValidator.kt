package com.suit.team.b.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

class EmailValidator(private val et: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        if (p0.isValidEmail()) {
            et.error = null
        } else {
            et.error = "Invalid email."
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    private fun CharSequence.isValidEmail(): Boolean {
        return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}
