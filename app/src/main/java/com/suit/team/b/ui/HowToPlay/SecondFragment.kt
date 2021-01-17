package com.suit.team.b.ui.HowToPlay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.suit.team.b.R

class SecondFragment : Fragment() {
    private var desc: String? = null
    private var gambar = 0
    private lateinit var listener: (String) -> Unit

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            desc = it.getString(ARG_PARAM1)
            gambar = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = view.findViewById(R.id.image)
        val text: TextView = view.findViewById(R.id.title)

        text.text = desc
        image.setImageResource(gambar)
        if (gambar == R.drawable.pvcom2) {
            listener("akhir")
        } else {
            listener("")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, gambar: Int, listener: (String) -> Unit) =
            SecondFragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, gambar)
                }
            }
    }
}
