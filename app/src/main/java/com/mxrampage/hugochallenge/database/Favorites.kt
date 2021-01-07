package com.mxrampage.hugochallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("SpellCheckingInspection")
@Entity(tableName = "favorites_table")
data class Favorites(
    @PrimaryKey
    val id: Long,
    val name: String,
    val imageURL: String,
    val ph: Float
)
