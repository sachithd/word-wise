package com.sachith.wordwise

import com.google.gson.Gson
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.DictionaryApiInterface
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

class RetrofitTest {

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
        val result = apiHelper.getDefinition(TestUtil.TEST_WORD_VALID)

        // verify the response is OK
        assertEquals(200, result.code())
    }

    @Test
    fun `can get valid definition for word hello`() = runTest {

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(TestUtil.TEST_DEFINITION))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = apiHelper.getDefinition(TestUtil.TEST_WORD_VALID)

        assertEquals(actualResponse.body(), TestUtil.TEST_DEFINITION)
    }

    @Test
    fun `get definition for an invalid word, api must return with http code 404`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = apiHelper.getDefinition(TestUtil.TEST_WORD_INVALID)
        assertEquals(actualResponse.code(), HttpURLConnection.HTTP_NOT_FOUND)
    }



}