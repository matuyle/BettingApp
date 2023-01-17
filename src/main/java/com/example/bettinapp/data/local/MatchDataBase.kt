package com.example.bettinapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bettinapp.data.local.entity.MatchEntity


@Database(
    entities = [MatchEntity::class],
    version = 1
)
abstract class MatchDataBase : RoomDatabase() {

    abstract val dao: MatchDao
}