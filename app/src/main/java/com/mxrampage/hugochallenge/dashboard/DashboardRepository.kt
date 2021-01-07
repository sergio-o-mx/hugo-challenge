package com.mxrampage.hugochallenge.dashboard

import com.mxrampage.hugochallenge.database.FavoritesDAO
import com.mxrampage.hugochallenge.network.APIService
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: APIService,
    private val favoritesDAO: FavoritesDAO
) {
    suspend fun getBeersFromNetwork() = apiService.getBeersFromNetwork()
    suspend fun searchBeersOnNetwork(beerName: String) = apiService.searchBeersOnNetwork(beerName)
    suspend fun getFavoritesFromLocalStorage() = favoritesDAO.getFavoritesFromLocalStorage()
}
