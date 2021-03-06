package com.suit.team.b.ui.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.suit.team.b.R

class TutorialFragment : Fragment() {
    private var desc: String? = null
    private var lottieFile = 0
    private lateinit var listener: (String) -> Unit

    private val argParamOne = "param1"
    private val argParamTwo = "param2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            desc = it.getString(argParamOne)
            lottieFile = it.getInt(argParamTwo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text: TextView = view.findViewById(R.id.tvTutorialTitle)
        val lottie: LottieAnimationView = view.findViewById(R.id.lottie_tutorial)

        text.text = desc
        lottie.setAnimation(lottieFile)
        if (lottieFile == R.drawable.ic_pvcom2) {
            listener(getString(R.string.last))
        } else {
            listener("")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, gambar: Int, listener: (String) -> Unit) =
            TutorialFragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(argParamOne, param1)
                    putInt(argParamTwo, gambar)
                }
            }
    }
}
