package com.suit.team.b.ui.profile.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.suit.team.b.R

class DialogFragmentDelete(private val profileView: ProfileView) : DialogFragment() {

    private var presenter: ProfilePresenter? = null
    private var btDelete: Button? = null
    private var btCancel: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ProfilePresenterImp(profileView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.delete_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btDelete = view.findViewById(R.id.btDelete)
        btCancel = view.findViewById(R.id.btCancel)

        btDelete?.setOnClickListener {
            presenter?.deleteProfile()
            this.dismiss()
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
