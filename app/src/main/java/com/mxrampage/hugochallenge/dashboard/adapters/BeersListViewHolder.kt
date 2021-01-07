package com.mxrampage.hugochallenge.dashboard.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.dashboard.Beers

class BeersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageBeerDisplay: ImageView = itemView.findViewById(R.id.imageBeerDisplay)
    private val textBeerName: TextView = itemView.findViewById(R.id.textBeerName)
    private val textBeerPh: TextView = itemView.findViewById(R.id.textBeerPh)

    fun bind(beer: Beers, onItemClick: ((Beers) -> Unit)) {
        if (beer.imageURL != null) {
            Glide.with(imageBeerDisplay.context)
                .load(beer.imageURL)
                .into(imageBeerDisplay)
        }
        textBeerName.text = beer.name
        var ph = 0f
        if (beer.ph != null) {
            ph = beer.ph
        }
        val beerPh = "p.h.: $ph"
        textBeerPh.text = beerPh
        itemView.setOnClickListener {
            onItemClick(beer)
        }
    }
}
