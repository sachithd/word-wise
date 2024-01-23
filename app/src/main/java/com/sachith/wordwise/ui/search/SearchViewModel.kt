package com.sachith.wordwise.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachith.wordwise.api.DictionaryRepository
import com.sachith.wordwise.api.dto.DefinitionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val dictionaryRepository = DictionaryRepository()
    private val _onlineDefinition = MutableLiveData<List<DefinitionDto>>()
    val onlineDefinition: LiveData<List<DefinitionDto>> = _onlineDefinition

    fun getDefinitions(mQuery: String){
        viewModelScope.launch(Dispatchers.IO){
            _onlineDefinition.postValue(dictionaryRepository.getDefinition(mQuery).body())
        }
    }
}