package com.example.bettinapp.domain.repository

class FakeDataStoreRepository : DataStoreRepository {
    var map = hashMapOf<String, String>()
    override suspend fun putString(key: String, value: String) {
        map[key] = value
    }

    override suspend fun getString(key: String): String {
        return map.getValue(key)
    }
}