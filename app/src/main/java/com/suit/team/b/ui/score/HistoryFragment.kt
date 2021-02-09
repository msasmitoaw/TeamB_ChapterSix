package com.suit.team.b.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.utils.string

class HistoryFragment(private val page: String) : Fragment() {

    private var viewModel: ScoreViewModel? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.history_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        recyclerView = view.findViewById(R.id.rvHistory)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)

        if (page == this.context?.string(R.string.history)) {
            viewModel?.fetchBattleHistory()
            viewModel?.battleHistory?.observe(viewLifecycleOwner) {
                recyclerView?.adapter =
                    viewModel?.let { vm -> HistoryRVAdapter(bookmark = false, it, vm) }
            }
        }

        if (page == this.context?.string(R.string.bookmark)) {
            viewModel?.fetchBookmark()
            viewModel?.bmHistory?.observe(viewLifecycleOwner) {
                recyclerView?.adapter =
                    viewModel?.let { vm -> HistoryRVAdapter(bookmark = true, it, vm) }
            }
        }

        viewModel?.errorRegister?.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
