package com.example.bettinapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bettinapp.data.local.entity.MatchEntity
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.model.MatchAndResult
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches_table")
    fun getMatches(): Flow<List<MatchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches_table WHERE id = :id")
    suspend fun getMatchById(id: Int): Match?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity)

    @Query("SELECT * FROM matches_table WHERE team1_prediction NOT NULL AND team2_prediction NOT NULL")
    suspend fun getMatchWithPrediction(): Match?

    @Query("DELETE FROM matches_table")
    suspend fun clearMatches()

    @Query(
        "SELECT \n" +
            "matches_table.id as id, \n" +
            "matches_table.team1 as team1, \n" +
            "matches_table.team2 as team2, \n" +
            "matches_table.team1_prediction as team1_prediction, \n" +
            "matches_table.team2_prediction as team2_prediction, \n" +
            "result_table.team1_points as team1_points, \n" +
            "result_table.team2_points as team2_points \n" +
            "FROM matches_table, result_table \n" +
        "WHERE \n" +
            "result_table.team1 = matches_table.team1 AND \n" +
            "result_table.team2 = matches_table.team2 AND \n" +
            "matches_table.team1_prediction NOT NULL AND \n" +
            "matches_table.team2_prediction NOT NULL"
    )
    fun getMatchesAndResults(): List<MatchAndResult>

}