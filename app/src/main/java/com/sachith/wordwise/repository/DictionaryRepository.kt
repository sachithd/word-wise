package com.sachith.wordwise.repository

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.DictionaryApiInterface
import com.sachith.wordwise.api.RetrofitClient
import com.sachith.wordwise.api.dto.DefinitionDto
import com.sachith.wordwise.database.dao.DictionaryDao
import com.sachith.wordwise.database.database.DictionaryDatabase.Companion.getDatabase
import com.sachith.wordwise.database.model.DefinitionModel

class DictionaryRepository(dictionaryApplication: Application)  {

    private var dictionaryApiInterface: DictionaryApiInterface = RetrofitClient.getInstance()
        .create(DictionaryApiInterface::class.java)

    private var apiHelper: DictionaryApiHelper = DictionaryApiHelper(dictionaryApiInterface)
    private var dictionaryDao: DictionaryDao = getDatabase(dictionaryApplication).dictionaryDao()


    // Fetch definition from the data sources (local database and network)
    /*suspend fun getDefinition(mWord: String): Response<List<DefinitionDto>> {
        val cachedDefinition = dictionaryDao.getDefinition(mWord)

        //return if (cachedDefinition.isNotEmpty()) {
        //    cachedDefinition
        //} else {
            val definitions = apiHelper.getDefinition(mWord)
            //userDao.insertUsers(networkUsers)
        return definitions
        //}
    }*/

    /**
     * Fetch definition from the data sources (local database and network)
     *
     * @param mWord
     * @return A List of definitions
     */
    suspend fun getDefinition(mWord: String): List<DefinitionDto> {
        val cachedDefinitionList = dictionaryDao.getDefinition(mWord)
        if (cachedDefinitionList.isNotEmpty()){
            val cachedDefinition = cachedDefinitionList[0].definition //This will be a json string
            if(cachedDefinition.isNotEmpty()) {
                val definitionDtoListType = object : TypeToken<List<DefinitionDto>>() {}.type
                Log.d("DictionaryRepository", "Found definition in the database")
                return Gson().fromJson(cachedDefinition, definitionDtoListType)
            }
        }

        //If the definition is not found in the database call the API
        try {
            val result = apiHelper.getDefinition(mWord)
            if(result.isSuccessful){
                val definitionObject = result.body()
                if (definitionObject != null) {
                    Log.d("DictionaryRepository", "Found definition in the API")

                    //Insert the definition in the database
                    val definitionModel = DefinitionModel(
                        0,
                        mWord,
                        Gson().toJson(definitionObject)
                    )
                    try {
                        dictionaryDao.insertAll(definitionModel)
                    }
                    catch (e: Exception){
                        Log.e("DictionaryRepository", "An error occurred whilst inserting record into the database")
                    }
                    return definitionObject
                }
            }
        }
        catch (_: Exception){
            Log.e("DictionaryRepository", "An error occurred whilst querying the API")
        }

        return arrayListOf(DefinitionDto())
    }
}