package com.sachith.wordwise.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dictionary",
    indices = [
        Index(name = "idx_word", value = ["word"], unique = true), //Creates a database index on word column
    ]
)
data class DefinitionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val definition: String,
)
