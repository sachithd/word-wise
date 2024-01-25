package com.sachith.wordwise.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachith.wordwise.api.dto.Definition
import com.sachith.wordwise.repository.DictionaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel (
    private val dictionaryRepository: DictionaryRepository,
) : ViewModel() {
    //private val dictionaryRepository = DictionaryRepository(application)
    private val _definition = MutableLiveData<String>()
    val definition: LiveData<String> = _definition

    /**
     * Get the definition for the given word from the repository, process the result and post the result
     *
     * @param mWord Word to look up
     */
    fun getDefinition(mWord: String){
        viewModelScope.launch(Dispatchers.IO){
            _definition.postValue(processResult(dictionaryRepository.getDefinition(mWord)))
        }
    }

    /**
     * Process the result set to build a HTML Formatted string
     *
     * @param definitionList list of DefinitionDto objects
     * @return list of definitions as a HTML formatted string
     */
    private fun processResult(definitionList: List<Definition>): String {
        val resultString = StringBuilder()
        for (definitionItem in definitionList){
            val meaningList = definitionItem.meanings
            if(!meaningList.isNullOrEmpty()){
                for (meaning in meaningList){
                    val definitionsList = meaning.definitions
                    if(definitionsList.isNotEmpty()){
                        for (definitions in definitionsList){
                            resultString.append(definitions.definition).append("<br/><br/>")
                        }
                    }
                }
            }


        }

        return resultString.toString()
    }
}