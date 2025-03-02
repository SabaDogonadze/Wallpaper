package com.example.wallpaperapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.wallpaperapp.data.collection.CollectionRepositoryImpl
import com.example.wallpaperapp.data.collection.CollectionSearchService
import com.example.wallpaperapp.data.collection.CollectionService
import com.example.wallpaperapp.data.collection_discovery.CollectionDiscoveryRepositoryImpl
import com.example.wallpaperapp.data.collection_discovery.CollectionDiscoveryService
import com.example.wallpaperapp.data.datastore.DataStoreRepositoryImpl
import com.example.wallpaperapp.data.detail.DetailRepositoryImpl
import com.example.wallpaperapp.data.detail.DetailService
import com.example.wallpaperapp.data.discovery.DiscoveryRepositoryImpl
import com.example.wallpaperapp.data.discovery.DiscoveryService
import com.example.wallpaperapp.data.discovery.search.SearchService
import com.example.wallpaperapp.data.favourite.LocalFavouriteRepositoryImpl
import com.example.wallpaperapp.data.login.LogInRepositoryImpl
import com.example.wallpaperapp.data.register.RegisterRepositoryImpl
import com.example.wallpaperapp.domain.collection.CollectionRepository
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryRepository
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.detail.DetailRepository
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import com.example.wallpaperapp.domain.login.LoginRepository
import com.example.wallpaperapp.domain.register.RegisterRepository
import com.example.wallpaperapp.local.dao.FavouriteDao
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

    @Singleton
    @Provides
    fun provideDetailRepository(detailService: DetailService): DetailRepository {
        return DetailRepositoryImpl(detailService)
    }
    @Singleton
    @Provides
    fun provideCollectionRepository(collectionService: CollectionService,collectionSearchService: CollectionSearchService): CollectionRepository {
        return CollectionRepositoryImpl(collectionService,collectionSearchService)
    }

    @Singleton
    @Provides
    fun provideCollectionDiscoveryRepository(collectionDiscoveryService: CollectionDiscoveryService): CollectionDiscoveryRepository {
        return CollectionDiscoveryRepositoryImpl(collectionDiscoveryService)
    }

    @Singleton
    @Provides
    fun provideLocalFavouriteRepository(favouriteDao: FavouriteDao): LocalFavouriteRepository {
        return LocalFavouriteRepositoryImpl(favouriteDao)
    }

}