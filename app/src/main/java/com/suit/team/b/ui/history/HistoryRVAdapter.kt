package com.suit.team.b.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.Battle
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.Score

class HistoryRVAdapter (private val history: MutableList<Score>?) :
    RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.score_item_view, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, pos: Int) {
        val model = history?.get(pos)
        if (model != null) {
            holder.bind(model)
        }
        val ivWinner = holder.itemView.findViewById<ImageView>(R.id.lavWinner)
        if (pos == 0 && (history?.get(0)?.scoreValue != history?.get(1)?.scoreValue)) {
            ivWinner.visibility = View.VISIBLE
        } else ivWinner.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int = 2

    class HistoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var tvHisotryScore = v.findViewById<TextView>(R.id.tvHistoryScore)
        private var tvBookmark = v.findViewById<TextView>(R.id.tvBookmark)

        fun bind(model: Score) {
            tvHisotryScore.text = model.scoreValue.toString()
            tvBookmark.text = ""
        }
    }
}