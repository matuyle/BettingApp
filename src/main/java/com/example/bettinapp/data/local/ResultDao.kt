package com.example.bettinapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bettinapp.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Query("SELECT * FROM result_table")
    fun getResults(): Flow<List<ResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(matches: List<ResultEntity>)

    @Query("DELETE FROM result_table")
    suspend fun clearResults()

}