package com.example.wallpaperapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.wallpaperapp.data.datastore.DataStoreRepositoryImpl
import com.example.wallpaperapp.data.discovery.DiscoveryRepositoryImpl
import com.example.wallpaperapp.data.discovery.DiscoveryService
import com.example.wallpaperapp.data.discovery.search.SearchService
import com.example.wallpaperapp.data.login.LogInRepositoryImpl
import com.example.wallpaperapp.data.register.RegisterRepositoryImpl
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import com.example.wallpaperapp.domain.login.LoginRepository
import com.example.wallpaperapp.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLogInRepository():LoginRepository{
        return  LogInRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideRegisterRepository():RegisterRepository{
        return  RegisterRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideDataStore(dataStore: DataStore<Preferences>): DataStoreRepository {
        return  DataStoreRepositoryImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideDiscoveryRepository(discoveryService: DiscoveryService,searchService: SearchService): DiscoveryRepository {
        return DiscoveryRepositoryImpl(discoveryService,searchService)
    }
}