package com.example.bettinapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bettinapp.data.local.entity.MatchEntity

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches_table")
    fun getMatches(): List<MatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)

    @Query("DELETE FROM matches_table")
    suspend fun clearAll()
}