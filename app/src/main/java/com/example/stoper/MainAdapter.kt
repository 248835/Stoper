package com.example.stoper

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var data = listOf<Tag>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var fontColor: Int? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item, fontColor)
    }

    override fun getItemCount() = data.size

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val timeNumber: TextView = itemView.findViewById(R.id.time_number)
        private val elapsedTime: TextView = itemView.findViewById(R.id.elapsed_time)
        private val time: TextView = itemView.findViewById(R.id.time)

        fun bind(item: Tag, fontColor: Int? = null){
            timeNumber.text = "Pomiar ${item.id}"
            elapsedTime.text = "Upłynęło ${item.timeBetween.convertLongToTime()}.%03d".format(item.timeBetween%1000)
            time.text = "${item.timeTaken.convertLongToTime()}.%03d".format(item.timeTaken%1000)
            fontColor?.let {
                // todo alpha
                timeNumber.setTextColor(it)
                elapsedTime.setTextColor(it)
                time.setTextColor(it)
            }
        }

        private fun Long.convertLongToTime(): String {
            val date = Date(this)
            val format = SimpleDateFormat("mm:ss", Locale.getDefault())
            return format.format(date)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.time_item_layout, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

