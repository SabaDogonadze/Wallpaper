package com.example.wallpaperapp.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallpaperapp.local.dao.FavouriteDao
import com.example.wallpaperapp.local.entity.FavouriteEntity

@Database(entities = [FavouriteEntity::class],version =1 )
abstract class AppDataBase:RoomDatabase() {
    abstract fun favouriteDao():FavouriteDao
}