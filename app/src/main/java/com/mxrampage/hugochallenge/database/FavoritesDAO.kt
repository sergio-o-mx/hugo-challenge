package com.mxrampage.hugochallenge.database

import androidx.room.*

@Dao
interface FavoritesDAO {
    @Query("SELECT * FROM favorites_table ORDER BY id DESC")
    suspend fun getFavoritesFromLocalStorage(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFavoriteInLocalStorage(favorite: Favorites)

    @Delete
    suspend fun deleteFavoriteFromLocalStorage(favorite: Favorites)
}
