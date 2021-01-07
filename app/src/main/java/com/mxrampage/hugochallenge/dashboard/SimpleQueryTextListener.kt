package com.mxrampage.hugochallenge.dashboard

import android.widget.SearchView

interface SimpleQueryTextListener : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
