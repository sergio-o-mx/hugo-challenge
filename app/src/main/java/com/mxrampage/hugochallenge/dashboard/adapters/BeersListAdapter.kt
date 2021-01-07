package com.mxrampage.hugochallenge.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.dashboard.Beers

class BeersListAdapter : ListAdapter<Beers, BeersListViewHolder>(BeersDiffUtil()) {
    var onItemClick: ((Beers) -> Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.beer_list_item, parent, false)
        return BeersListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeersListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    internal class BeersDiffUtil : DiffUtil.ItemCallback<Beers>() {
        override fun areItemsTheSame(oldItem: Beers, newItem: Beers): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beers, newItem: Beers): Boolean {
            return oldItem == newItem
        }
    }
}
