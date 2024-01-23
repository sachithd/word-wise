package com.sachith.wordwise.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sachith.wordwise.repository.DictionaryRepository
import com.sachith.wordwise.api.dto.DefinitionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel (application: Application) : AndroidViewModel(application) {
    private val dictionaryRepository = DictionaryRepository(application)
    private val _definition = MutableLiveData<List<DefinitionDto>>()
    val definition: LiveData<List<DefinitionDto>> = _definition

    fun getDefinitions(mQuery: String){
        viewModelScope.launch(Dispatchers.IO){
            _definition.postValue(dictionaryRepository.getDefinition(mQuery))
        }
    }
}