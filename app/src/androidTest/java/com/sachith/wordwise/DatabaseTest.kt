package com.sachith.wordwise

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.sachith.wordwise.database.dao.DictionaryDao
import com.sachith.wordwise.database.database.DictionaryDatabase
import com.sachith.wordwise.database.model.DefinitionModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest : TestCase() {
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var db: DictionaryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DictionaryDatabase::class.java
        ).build()
        dictionaryDao = db.dictionaryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertDefinitionAndRetrieveDefinition() = runBlocking {
        val definition = DefinitionModel(
            0,
            TestUtil.TEST_WORD_VALID,
            Gson().toJson(TestUtil.TEST_DEFINITION)
        )

        dictionaryDao.insertAll(definition)
        val databaseDefinition = dictionaryDao.getDefinition(TestUtil.TEST_WORD_VALID)

        if (databaseDefinition.isNotEmpty()) {
            Assert.assertEquals(databaseDefinition[0].definition, Gson().toJson(TestUtil.TEST_DEFINITION))
        }
    }

}