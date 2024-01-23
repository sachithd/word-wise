package com.sachith.wordwise.api

import com.sachith.wordwise.api.dto.DefinitionDto
import retrofit2.Response

class DictionaryRepository()  {

    private var dictionaryApiInterface: DictionaryApiInterface = RetrofitClient.getInstance().create(DictionaryApiInterface::class.java)

    private var apiHelper: DictionaryApiHelper = DictionaryApiHelper(dictionaryApiInterface)

    // Fetch definition from the data sources (local database and network)
    suspend fun getDefinition(query: String): Response<List<DefinitionDto>> {
        //val cachedDefinition = userDao.getAllUsers()

        //return if (cachedDefinition.isNotEmpty()) {
        //    cachedDefinition
        //} else {
            val definitions = apiHelper.getDefinition(query)
            //userDao.insertUsers(networkUsers)
        return definitions
        //}
    }
}