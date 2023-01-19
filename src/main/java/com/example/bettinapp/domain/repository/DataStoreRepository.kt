package com.example.bettinapp.domain.repository

interface DataStoreRepository {
    suspend fun putString(key:String,value:String)
    suspend fun getString(key: String):String
}