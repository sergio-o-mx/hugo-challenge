package com.mxrampage.hugochallenge.detail

import com.mxrampage.hugochallenge.database.FavoritesDAO
import com.mxrampage.hugochallenge.database.Favorites
import com.mxrampage.hugochallenge.network.APIService
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val apiService: APIService,
    private val favoritesDAO: FavoritesDAO
) {
    suspend fun getBeersDetailsFromNetwork(beerId: Long) =
        apiService.getBeerDetailsFromNetwork(beerId)

    suspend fun saveFavoriteInLocalStorage(favorite: Favorites) =
        favoritesDAO.saveFavoriteInLocalStorage(favorite)

    suspend fun deleteFavoriteFromLocalStorage(favorite: Favorites) =
        favoritesDAO.deleteFavoriteFromLocalStorage(favorite)
}
