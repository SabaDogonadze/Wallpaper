package com.example.wallpaperapp.di

import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.collection.CollectionSearchService
import com.example.wallpaperapp.data.collection.CollectionService
import com.example.wallpaperapp.data.detail.DetailService
import com.example.wallpaperapp.data.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.data.discovery.DiscoveryService
import com.example.wallpaperapp.data.discovery.search.SearchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(logging)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client:OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().baseUrl(com.example.wallpaperapp.BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                )
            ).build()
    }
    @Singleton
    @Provides
    fun provideGetDiscoveryImageService(retrofit: Retrofit): DiscoveryService {
        return retrofit.create(DiscoveryService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetDiscoverySearchImageService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailImageService(retrofit: Retrofit): DetailService {
        return retrofit.create(DetailService::class.java)
    }

    @Singleton
    @Provides
    fun provideCollectionService(retrofit: Retrofit): CollectionService {
        return retrofit.create(CollectionService::class.java)
    }

    @Singleton
    @Provides
    fun provideCollectionSearchService(retrofit: Retrofit): CollectionSearchService {
        return retrofit.create(CollectionSearchService::class.java)
    }

}