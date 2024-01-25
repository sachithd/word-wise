package com.sachith.wordwise.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sachith.wordwise.SearchApplication
import com.sachith.wordwise.api.DictionaryApiHelper
import com.sachith.wordwise.api.DictionaryApiInterface
import com.sachith.wordwise.repository.DictionaryRepository

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {

            val dictionaryDao = SearchApplication.dictionaryDatabase.dictionaryDao()
            val retrofitApiHelper = DictionaryApiHelper(SearchApplication.retrofitClient.create(DictionaryApiInterface::class.java))

            // Create DictionaryRepository instance
            val dictionaryRepository = DictionaryRepository(retrofitApiHelper,dictionaryDao )

            // Create ViewModel
            val searchViewModel = SearchViewModel(dictionaryRepository)

            // If the requested ViewModel is SearchViewModel, create an instance with the provided dependencies
            @Suppress("UNCHECKED_CAST")
            return (searchViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}