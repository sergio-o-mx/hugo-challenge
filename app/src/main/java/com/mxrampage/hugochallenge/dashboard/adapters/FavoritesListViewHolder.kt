package com.mxrampage.hugochallenge.dashboard.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.database.Favorites

class FavoritesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageBeerDisplay: ImageView = itemView.findViewById(R.id.imageBeerDisplay)
    private val textBeerName: TextView = itemView.findViewById(R.id.textBeerName)

    fun bind(favorite: Favorites, onItemClick: ((Favorites) -> Unit)) {
        if (favorite.imageURL.isNotEmpty()) {
            Glide.with(imageBeerDisplay.context)
                .load(favorite.imageURL)
                .into(imageBeerDisplay)
        }
        textBeerName.text = favorite.name
        itemView.setOnClickListener {
            onItemClick(favorite)
        }
    }
}
