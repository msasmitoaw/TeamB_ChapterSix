package com.suit.team.b.ui.profile.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.suit.team.b.R

class UpdateDialogFragment(private val updateView: UpdateView) : DialogFragment() {

    private var btSave: Button? = null
    private var btCancel: Button? = null
    private var etPassword: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.update_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSave = view.findViewById(R.id.btSave)
        btCancel = view.findViewById(R.id.btCancel)
        etPassword = view.findViewById(R.id.etPassword)

        btSave?.setOnClickListener {
            if (etPassword?.text?.length!! > 0) {
                updateView.onChangedDataReady(etPassword?.text.toString())
            } else {
                Toast.makeText(
                    this.context,
                    getString(R.string.password_request),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btCancel?.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
    }
}
