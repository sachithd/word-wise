package com.sachith.wordwise

import android.app.Application
import com.sachith.wordwise.api.RetrofitClient
import com.sachith.wordwise.database.database.DictionaryDatabase
import retrofit2.Retrofit

class SearchApplication : Application() {
    companion object {
        lateinit var retrofitClient: Retrofit
        lateinit var dictionaryDatabase: DictionaryDatabase
    }
    override fun onCreate() {
        super.onCreate()
        dictionaryDatabase = DictionaryDatabase.getDatabase(applicationContext)
        retrofitClient = RetrofitClient.getInstance()
    }
}