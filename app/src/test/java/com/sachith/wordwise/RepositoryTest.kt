package com.sachith.wordwise

import com.google.gson.Gson
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.DictionaryApiInterface
import com.sachith.wordwise.api.dto.DefinitionDto
import com.sachith.wordwise.api.dto.Definitions
import com.sachith.wordwise.api.dto.License
import com.sachith.wordwise.api.dto.Meanings
import com.sachith.wordwise.api.dto.Phonetics
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var dictionaryApiInterface: DictionaryApiInterface
    private lateinit var apiHelper: DictionaryApiHelper

    @Before
    fun setUp() {
        // Start the MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Create a Retrofit instance with the MockWebServer URL
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of your Retrofit service
        dictionaryApiInterface = retrofit.create(DictionaryApiInterface::class.java)

        apiHelper = DictionaryApiHelper(dictionaryApiInterface)
    }

    @After
    fun tearDown() {
        // Shutdown the MockWebServer
        mockWebServer.shutdown()
    }

    @Test
    fun `can call dictionary api`() = runTest {
        // Perform the actual API call using your Retrofit service
        val result = apiHelper.getDefinition("hello")

        // verify the response is OK
        assertEquals(200, result.code())
    }


    @Test
    fun `can get valid definition for word hello`() = runTest {

        val meaningsDefinitions1 = listOf(
            Definitions(
                definition = "\"Hello!\" or an equivalent greeting.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
            )
        )
        val meaningsDefinitions2 = listOf(
            Definitions(
                definition = "To greet with \"hello\".",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
            )
        )
        val meaningsDefinitions3 = listOf(
            Definitions(
                definition = "A greeting (salutation) said when meeting someone or acknowledging someone’s arrival or presence.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
                example = "Hello, everyone."
            ),
            Definitions(
                definition = "A greeting used when answering the telephone.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
                example = "Hello? How may I help you?"
            ),
            Definitions(
                definition = "A call for response if it is not clear if anyone is present or listening, or if a telephone conversation may have been disconnected.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
                example = "Hello? Is anyone there?"
            ),
            Definitions(
                definition = "Used sarcastically to imply that the person addressed or referred to has done something the speaker or writer considers to be foolish.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
                example = "You just tried to start your car with your cell phone. Hello?"
            ),
            Definitions(
                definition = "An expression of puzzlement or discovery.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
                example = "Hello! What’s going on here?"
            )
        )

        val meanings = listOf(
            Meanings(
                partOfSpeech = "noun",
                definitions = ArrayList(meaningsDefinitions1),
                synonyms=arrayListOf("greeting"),
                antonyms = arrayListOf(),
            ),
            Meanings(
                partOfSpeech = "verb",
                definitions = ArrayList(meaningsDefinitions2),
                synonyms=arrayListOf(),
                antonyms = arrayListOf(),
            ),
            Meanings(
                partOfSpeech = "interjection",
                definitions = ArrayList(meaningsDefinitions3),
                synonyms=arrayListOf(),
                antonyms = arrayListOf( "bye", "goodbye"),
            ),
        )

        val phonetics = listOf(
            Phonetics(
                audio = "https://api.dictionaryapi.dev/media/pronunciations/en/hello-au.mp3",
                sourceUrl = "https://commons.wikimedia.org/w/index.php?curid=75797336",
                license = License("BY-SA 4.0", "https://creativecommons.org/licenses/by-sa/4.0")
                ),
            Phonetics(
                text = "/həˈləʊ/",
                audio = "https://api.dictionaryapi.dev/media/pronunciations/en/hello-uk.mp3",
                sourceUrl= "https://commons.wikimedia.org/w/index.php?curid=9021983",
                license = License("BY 3.0 US","https://creativecommons.org/licenses/by/3.0/us")
            ),
            Phonetics(
                text = "/həˈloʊ/",
                audio = "",
            ),
        )

        val definition = listOf(
            DefinitionDto(
                word = "hello",
                phonetics = ArrayList(phonetics),
                meanings = ArrayList(meanings),
                license = License("CC BY-SA 3.0","https://creativecommons.org/licenses/by-sa/3.0"),
                sourceUrls = arrayListOf("https://en.wiktionary.org/wiki/hello")
            ),
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(definition))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = apiHelper.getDefinition("hello")

        assertEquals(actualResponse.body(), definition)
    }

    @Test
    fun `get definition for an invalid word, api must return with http code 404`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = apiHelper.getDefinition("hello-android")
        assertEquals(actualResponse.code(), HttpURLConnection.HTTP_NOT_FOUND)
    }



}