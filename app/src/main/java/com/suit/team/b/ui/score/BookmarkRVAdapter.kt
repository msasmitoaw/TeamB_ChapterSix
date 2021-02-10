package com.suit.team.b.ui.score

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.BattleBookmark
import com.suit.team.b.utils.getDateFromIso
import java.time.format.FormatStyle

class BookmarkRVAdapter(
    private val data: MutableList<BattleBookmark>,
) :
    RecyclerView.Adapter<BookmarkRVAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.bookmark_item, parent, false)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryViewHolder, pos: Int) {
        holder.bind(data[pos])
    }

    override fun getItemCount(): Int = data.size

    inner class HistoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val tvCreated = v.findViewById<TextView>(R.id.tvCreated)
        private val tvMessage = v.findViewById<TextView>(R.id.tvMessage)
        private val tvMode = v.findViewById<TextView>(R.id.tvMode)
        private val tvResult = v.findViewById<TextView>(R.id.tvResult)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: BattleBookmark) {
            tvCreated.text = getDateFromIso(data.createdAt, FormatStyle.MEDIUM)
            tvMessage.text = data.message
            tvMode.text = data.mode
            tvResult.text = data.result

        }
    }
}
