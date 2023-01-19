package com.example.bettinapp.di

import android.app.Application
import androidx.room.Room
import com.example.bettinapp.data.remote.Constants
import com.example.bettinapp.data.local.MatchDataBase
import com.example.bettinapp.data.remote.BettingApi
import com.example.bettinapp.data.repository.BettingRepositoryImpl
import com.example.bettinapp.domain.repository.BettingRepository
import com.example.bettinapp.domain.use_case.*
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
    fun provideBettingUseCases(repository: BettingRepository): MatchesUseCases {
        return MatchesUseCases(
            getMatchesUseCase = GetMatchesUseCase(repository),
            getMatchUseCase = GetMatchUseCase(repository),
            addMatchUseCase = AddMatchUseCase(repository),
            getMatchWithPrediction = GetMatchWithPrediction(repository),
            getResultsUseCase = GetResultsUseCase(repository),
            getMatchesAndResults = GetMatchesAndResults(repository),
            deleteTablesUseCase = DeleteTablesUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideBettingRepository(
        db: MatchDataBase,
        api: BettingApi
    ): BettingRepository {
        return BettingRepositoryImpl(
            api,
            db.matchDao,
            db.resultDao
        )
    }

    @Provides
    @Singleton
    fun provideMatchDatabase(app: Application): MatchDataBase {
        return Room.databaseBuilder(
            app,
            MatchDataBase::class.java,
            "match_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBettingApi(): BettingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BettingApi::class.java)
    }


}