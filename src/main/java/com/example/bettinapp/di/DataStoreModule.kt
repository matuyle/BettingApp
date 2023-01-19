package com.example.bettinapp.di

import android.content.Context
import com.example.bettinapp.data.repository.DataStoreRepositoryImpl
import com.example.bettinapp.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun providesDatastoreRepo(
        @ApplicationContext context: Context
    ):DataStoreRepository = DataStoreRepositoryImpl(context)

}