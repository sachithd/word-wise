package com.sachith.wordwise.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sachith.wordwise.database.model.DefinitionModel

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg definitionModel: DefinitionModel)

    @Query("SELECT * FROM dictionary WHERE word = :mWord LIMIT 1")
    suspend fun getDefinition(mWord: String): List<DefinitionModel>
}