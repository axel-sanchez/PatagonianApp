package com.example.patagonianapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.patagonianapp.R
import com.example.patagonianapp.data.room.LyricsDTO

/**
 * @author Axel Sanchez
 */
class LyricsAdapter(
    private val values: List<LyricsDTO>,
    private val itemClick: (LyricsDTO) -> Unit
) : RecyclerView.Adapter<LyricsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lyrics, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.artist.text = item.artist
        holder.title.text = item.title

        holder.itemView.setOnClickListener { itemClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val artist: TextView = view.findViewById(R.id.artist)
        val title: TextView = view.findViewById(R.id.title)
    }
}