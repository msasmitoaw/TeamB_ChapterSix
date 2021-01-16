package com.suit.team.b.ui.profile.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.suit.team.b.R
import com.suit.team.b.ui.profile.show.ProfileView

class UpdateDialogFragment(private val profileView: ProfileView) : DialogFragment() {

    private var btSave: Button? = null
    private var btCancel: Button? = null
    private var etPassword: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.update_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSave = view.findViewById<Button>(R.id.btDelete)
        btCancel = view.findViewById<Button>(R.id.btCancel)

        btSave?.setOnClickListener {
            // SHARED PREFERENCE PASSWORD HERE!!!!!
            if (etPassword?.text.toString() == "password") {
                profileView.onSuccess(
                    resources.getString(R.string.update_success)
                )
                this.dismiss()
            } else {
                profileView.onSuccess(
                    resources.getString(R.string.update_failed)
                )
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
