package com.rockpaperscissors.team.b.ui.profile.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.rockpaperscissors.team.b.R

class DeleteDialogFragment(private val profileView: ProfileView) : DialogFragment() {

    private var btDelete: Button? = null
    private var btCancel: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.delete_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btDelete = view.findViewById(R.id.btDelete)
        btCancel = view.findViewById(R.id.btCancel)

        btDelete?.setOnClickListener {
            profileView.onSuccess(resources.getString(R.string.profile_del_success))
        }

        btCancel?.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
    }
}