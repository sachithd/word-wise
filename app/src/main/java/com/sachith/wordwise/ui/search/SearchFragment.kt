package com.sachith.wordwise.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sachith.wordwise.R
import com.sachith.wordwise.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SearchFragment : Fragment(), CoroutineScope {

    companion object {
        fun newInstance() = SearchFragment()
    }
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val dictionaryApiHelper = DictionaryApiHelper(RetrofitClient.getInstance().create(DictionaryApiInterface::class.java))
        //val dictionaryDao = DictionaryDatabase.getDatabase(requireContext().applicationContext).dictionaryDao()

        // Create DictionaryRepository instance
        //val dictionaryRepository = DictionaryRepositoryNew(dictionaryApiHelper,dictionaryDao )

        // Create ViewModel using the factory
        val searchViewModelFactory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Make the textview scrollable when all the definitions doesn't fit on the screen
        binding.textViewDefinition.movementMethod = ScrollingMovementMethod()

        //Submit the search query when search key is pressed on the keyboard
        binding.searchView.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                launch {
                    delay(500)  //debounce timeOut
                    Log.d("SearchFragment", "IME_ACTION_SEARCH" + binding.searchView.editText.text)
                    getDefinitions(binding.searchView.editText.text)
                }
                return@setOnEditorActionListener false
            }
            true
        }

        //Observe the live data and update the UI (TextView) when there is a value
        viewModel.definition.observe(viewLifecycleOwner) {
            //Log.d("SearchFragment", "Definitions : $it")
            if(it.isNotEmpty()){
                binding.textViewDefinition.text = getFormattedText(it)
            }
            else{
                binding.textViewDefinition.text = ""
                Toast.makeText(context, getString(R.string.definition_not_found), Toast.LENGTH_SHORT).show()
            }

        }
    }

    /**
     * Calls the viewModel getDefinition method if the word to search is not empty
     *
     * @param mWord
     */
    private fun getDefinitions(mWord: Editable?) {
        if(!mWord.isNullOrBlank()){
            viewModel.getDefinition(mWord.toString())
        }
    }

    /**
     * HTML Formatted String
     *
     * @param text
     * @return formatted HTML string from given HTML
     */
    private fun getFormattedText(text: String): Spanned? {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

}