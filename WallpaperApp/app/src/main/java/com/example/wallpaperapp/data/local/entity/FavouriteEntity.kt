package com.example.wallpaperapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey val id:String,
    @ColumnInfo(name = "image_url") val imageUrl :String
)