package com.sachith.wordwise.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dictionary",
    indices = [
        Index(name = "idx_word", value = ["word"]),
    ]
)
data class DefinitionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val definition: String,
)
