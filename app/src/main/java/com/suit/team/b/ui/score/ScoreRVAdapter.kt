package com.suit.team.b.ui.score

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.Score

class ScoreRVAdapter(private val score: MutableList<Score>?) :
    RecyclerView.Adapter<ScoreRVAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScoreViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.score_item_view, parent, false)
    )

    override fun onBindViewHolder(holder: ScoreViewHolder, pos: Int) {
        val model = score?.get(pos)
        if (model != null) {
            holder.bind(model)
        }


        val lavWinner = holder.itemView.findViewById<ImageView>(R.id.lavWinner)
        if (pos == 0 && (score?.get(0)?.scoreValue != score?.get(1)?.scoreValue)) {
            lavWinner.visibility = VISIBLE
        } else lavWinner.visibility = INVISIBLE
    }

    override fun getItemCount(): Int = 2

    class ScoreViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var tvScore = v.findViewById<TextView>(R.id.tvResult)
        private var tvPlayer = v.findViewById<TextView>(R.id.tvCreated)

        fun bind(model: Score) {
            tvScore.text = model.scoreValue.toString()
            tvPlayer.text = model.name
        }
    }
}
