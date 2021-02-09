package com.suit.team.b.ui.history
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
import com.suit.team.b.data.model.Battle
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.Score
import com.suit.team.b.ui.score.ScoreRVAdapter
import com.suit.team.b.ui.score.ScoreViewModel
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.ScoreType

class HistoryFragment(private val scoreType: ScoreType) : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var viewModel: HistoryViewModel? = null
    private var battle: MutableList<Score>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.history_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.historyView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)


        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        recyclerView?.adapter = HistoryRVAdapter(battle)

        if (scoreType == ScoreType.HISTORY) {
            viewModel?.fetchScoreMulti()
            viewModel?.resultScore?.observe(viewLifecycleOwner) {
                recyclerView?.adapter = HistoryRVAdapter(it)
            }
        } else {
            //untuk bookmark
        }

        viewModel?.errorRegister?.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }

    }




}