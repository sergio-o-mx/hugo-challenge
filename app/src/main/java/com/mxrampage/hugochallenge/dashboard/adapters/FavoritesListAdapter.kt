package com.mxrampage.hugochallenge.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.database.Favorites

class FavoritesListAdapter : ListAdapter<Favorites, FavoritesListViewHolder>(FavoritesDiffUtil()) {
    var onItemClick: ((Favorites) -> Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.favorites_list_item, parent, false)
        return FavoritesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    internal class FavoritesDiffUtil : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem == newItem
        }
    }
}
