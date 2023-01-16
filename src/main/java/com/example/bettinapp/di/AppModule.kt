package com.example.bettinapp.di

import com.example.bettinapp.core.Constants
import com.example.bettinapp.data.remote.BettingApi
import com.example.bettinapp.data.repository.BettingRepositoryImpl
import com.example.bettinapp.domain.repository.BettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): BettingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BettingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: BettingApi): BettingRepository {
        return BettingRepositoryImpl(api)
    }
}