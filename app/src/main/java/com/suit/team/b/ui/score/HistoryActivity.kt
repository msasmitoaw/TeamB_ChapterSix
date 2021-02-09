package com.suit.team.b.ui.score

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suit.team.b.R
import com.suit.team.b.data.model.BattleResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private var viewModel: ScoreViewModel? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        recyclerView = findViewById(R.id.rvHistory)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            viewModel?.fetchResult()
        }

        viewModel?.battleResult?.observe(this) {
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = MarkedRVAdapter(it)
        }
    }
}

class MarkedRVAdapter(private val data: MutableList<BattleResponse.Data>) :
    RecyclerView.Adapter<MarkedRVAdapter.MarkedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MarkedViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.bookmarked_item, parent, false)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MarkedViewHolder, pos: Int) {
        holder.bind(data[pos])
    }

    override fun getItemCount(): Int = data.size

    class MarkedViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var tvCreated = v.findViewById<TextView>(R.id.tvCreated)
        private var tvMessage = v.findViewById<TextView>(R.id.tvMessage)
        private var tvMode = v.findViewById<TextView>(R.id.tvMode)
        private var tvResult = v.findViewById<TextView>(R.id.tvResult)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: BattleResponse.Data) {
            val f: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT
            val date = Instant.from(f.parse(data.createdAt))
            val ldt = LocalDateTime.ofInstant(date,ZoneOffset.UTC)
            tvCreated.text = ldt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            tvMessage.text = data.message
            tvMode.text = data.mode
            tvResult.text = data.result
        }
    }
}
