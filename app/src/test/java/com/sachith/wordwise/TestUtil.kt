package com.sachith.wordwise

import com.sachith.wordwise.api.dto.DefinitionDto
import com.sachith.wordwise.api.dto.Definitions
import com.sachith.wordwise.api.dto.License
import com.sachith.wordwise.api.dto.Meanings
import com.sachith.wordwise.api.dto.Phonetics

class TestUtil {
    companion object {


        private val meaningsDefinitions1 = listOf(
            Definitions(
                definition = "\"Hello!\" or an equivalent greeting.",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
            )
        )
        private val meaningsDefinitions2 = listOf(
            Definitions(
                definition = "To greet with \"hello\".",
                synonyms = arrayListOf(),
                antonyms = arrayListOf(),
            )
        )
        private val meaningsDefinitions3 = listOf(
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

        private val meanings = listOf(
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

        private val phonetics = listOf(
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

        private val definition = listOf(
            DefinitionDto(
                word = "hello",
                phonetics = ArrayList(phonetics),
                meanings = ArrayList(meanings),
                license = License("CC BY-SA 3.0","https://creativecommons.org/licenses/by-sa/3.0"),
                sourceUrls = arrayListOf("https://en.wiktionary.org/wiki/hello")
            ),
        )

        const val TEST_WORD_VALID = "hello"
        const val TEST_WORD_INVALID = "hello-lloyds"

        val TEST_DEFINITION = definition
    }
}