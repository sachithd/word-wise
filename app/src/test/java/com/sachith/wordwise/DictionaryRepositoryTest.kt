package com.sachith.wordwise

import com.google.gson.Gson
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.dto.Definition
import com.sachith.wordwise.database.dao.DictionaryDao
import com.sachith.wordwise.database.model.DefinitionModel
import com.sachith.wordwise.repository.DictionaryRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DictionaryRepositoryTest {

    @Mock
    private lateinit var mockDictionaryApiHelper: DictionaryApiHelper

    @Mock
    private lateinit var mockDictionaryDao: DictionaryDao

    private lateinit var dictionaryRepository: DictionaryRepository



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dictionaryRepository = DictionaryRepository(mockDictionaryApiHelper, mockDictionaryDao)
    }

    @Test
    fun `can fetch data from api success`() = runTest {
        // Mock the behavior of Retrofit when fetchDataFromNetwork is called

        // Mock data
        val mockResponse: Response<List<Definition>> = Response.success(TestUtil.TEST_DEFINITION)

        // Mock the behavior of DictionaryApiHelper
        `when`(mockDictionaryApiHelper.getDefinition(TestUtil.TEST_WORD_VALID)).thenReturn(mockResponse)

        // Call the method under test
        val result = dictionaryRepository.fetchDataFromNetwork(TestUtil.TEST_WORD_VALID)

        // Verify that the mockDictionaryApiHelper.getDefinition method was called with the correct argument
        verify(mockDictionaryApiHelper).getDefinition(TestUtil.TEST_WORD_VALID)


        // Verify the result
        assertEquals(mockResponse, result)
    }

    @Test
    fun `can fetch data from database success`() = runTest {
        // Mock data
        val mockDatabaseData = listOf(DefinitionModel(0, TestUtil.TEST_WORD_VALID, Gson().toJson(TestUtil.TEST_DEFINITION)))

        // Mock the behavior of DictionaryDao
        `when`(mockDictionaryDao.getDefinition(TestUtil.TEST_WORD_VALID)).thenReturn(mockDatabaseData)

        // Call the method under test
        val result = dictionaryRepository.fetchDataFromDatabase(TestUtil.TEST_WORD_VALID)

        // Verify that the dictionaryDao.getDefinition method was called with the correct argument
        verify(mockDictionaryDao).getDefinition(TestUtil.TEST_WORD_VALID)

        // Verify the result
        assertEquals(mockDatabaseData, result)
    }


    @Test
    fun `when getDefinitions called it should get definitions`() = runTest{
       // val searchViewModel = SearchViewModel(mockDictionaryRepository)
       // searchViewModel.getDefinition(TestUtil.TEST_WORD_VALID)

        //verify(mockDictionaryRepository).getDefinition(TestUtil.TEST_WORD_VALID)
    }

}