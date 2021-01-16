package com.suit.team.b.ui.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.Score

class ScoreRVAdapter(private val data: MutableList<Score>) :
    RecyclerView.Adapter<ScoreRVAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScoreViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.score_item_view, parent, false)
    )

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val model = data[position]
        holder.bind(model)
    }

    override fun getItemCount() = data.size

    class ScoreViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var tvScore = v.findViewById<TextView>(R.id.tvScore)
        private var tvPlayer = v.findViewById<TextView>(R.id.tvPlayer)

        fun bind(model: Score) {
            tvScore.text = model.scoreValue.toString()
            tvPlayer.text = model.name
        }
    }
}
