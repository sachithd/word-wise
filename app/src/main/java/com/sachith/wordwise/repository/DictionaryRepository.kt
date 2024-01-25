package com.sachith.wordwise.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.dto.Definition
import com.sachith.wordwise.database.dao.DictionaryDao
import com.sachith.wordwise.database.model.DefinitionModel
import retrofit2.Response

class DictionaryRepository(private val apiHelper: DictionaryApiHelper,
                           private val dictionaryDao: DictionaryDao) {


    suspend fun fetchDataFromNetwork(mWord: String): Response<List<Definition>> {
        // Use Retrofit to fetch data from the network
        return apiHelper.getDefinition(mWord)
    }

    suspend fun fetchDataFromDatabase(mWord: String): List<DefinitionModel> {
        // Use Room to fetch data from the local database
        return dictionaryDao.getDefinition(mWord)
    }


    /**
     * Fetch definition from the data sources (either local database or network)
     *
     * @param mWord
     * @return A List of definitions
     */
    suspend fun getDefinition(mWord: String): List<Definition> {

        //Check if the definition exist in the local database
        val cachedDefinitionList = fetchDataFromDatabase(mWord)
        if (cachedDefinitionList.isNotEmpty()){
            val cachedDefinition = cachedDefinitionList[0].definition //This will be a json string
            if(cachedDefinition.isNotEmpty()) {
                Log.d("DictionaryRepository", "Found definition in the database")
                val definitionListType = object : TypeToken<List<Definition>>() {}.type
                return Gson().fromJson(cachedDefinition, definitionListType)
            }
        }

        //If the definition is not found in the database, then call the API
        try {
            val result = fetchDataFromNetwork(mWord)
            if(result.isSuccessful){
                val definitionObject = result.body()
                if (definitionObject != null) {
                    Log.d("DictionaryRepository", "Found definition in the API")

                    //Insert the definition to the local database
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

        return arrayListOf(Definition())
    }

}