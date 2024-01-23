package com.sachith.wordwise.api

import com.sachith.wordwise.api.dto.DefinitionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DictionaryApiInterface {
    @GET
    suspend fun getDefinition(@Url url: String?): Response<List<DefinitionDto>>
}