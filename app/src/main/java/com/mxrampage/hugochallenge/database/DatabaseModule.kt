package com.mxrampage.hugochallenge.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFavoritesDatabase(@ApplicationContext appContext: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoritesDatabase::class.java,
            "favorites_database"
        ).build()
    }

    @Provides
    fun provideFavoritesDAO(favoritesDatabase: FavoritesDatabase): FavoritesDAO {
        return favoritesDatabase.favoritesDao()
    }
}
