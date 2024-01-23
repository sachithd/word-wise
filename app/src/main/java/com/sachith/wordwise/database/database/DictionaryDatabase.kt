package com.sachith.wordwise.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sachith.wordwise.database.dao.DictionaryDao
import com.sachith.wordwise.database.model.DefinitionModel

private const val DATABASE_NAME = "wordwise_dictionary.db"

@Database(
    entities = [
        DefinitionModel::class,
    ],
    version = 1,
    exportSchema = true,
)

abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {

        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private fun buildDatabase(context: Context): DictionaryDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DictionaryDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }
    }

}