package com.mxrampage.hugochallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDAO
}
