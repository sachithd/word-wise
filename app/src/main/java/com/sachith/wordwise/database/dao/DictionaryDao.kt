package com.sachith.wordwise.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sachith.wordwise.database.model.DefinitionModel

@Dao
interface DictionaryDao {

    /**
     * Insert one of more definition objects, replace if a duplicate entry
     *
     * @param definitionModel
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg definitionModel: DefinitionModel)

    /**
     * Returns definitions for a given word
     *
     * @param mWord
     * @return
     */
    @Query("SELECT * FROM dictionary WHERE word = :mWord")
    suspend fun getDefinition(mWord: String): List<DefinitionModel>
}