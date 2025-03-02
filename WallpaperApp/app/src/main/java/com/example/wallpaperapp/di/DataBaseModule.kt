package com.example.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import com.example.wallpaperapp.local.dao.FavouriteDao
import com.example.wallpaperapp.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context):AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,"wallpaper-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteDao(appDataBase: AppDataBase):FavouriteDao{
        return appDataBase.favouriteDao()
    }
}