package com.example.bettinapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bettinapp.data.local.entity.MatchEntity
import com.example.bettinapp.data.local.entity.ResultEntity


@Database(
    entities = [MatchEntity::class, ResultEntity::class],
    version = 1
)
abstract class MatchDataBase : RoomDatabase() {
    abstract val matchDao: MatchDao
    abstract val resultDao: ResultDao
}