package com.sachith.wordwise.api

class DictionaryApiHelper (private val dictionaryApiInterface: DictionaryApiInterface) {
    companion object {
        const val BASE_URL: String = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    }

    /**
     * Append the word to construct the end point
     *
     * @param mQuery
     * @return full API end point
     */
    private fun getDictionaryApiURL(mQuery: String): String {
        return "$BASE_URL$mQuery"
    }

    suspend fun getDefinition(query: String) = dictionaryApiInterface.getDefinition(getDictionaryApiURL(query))
}