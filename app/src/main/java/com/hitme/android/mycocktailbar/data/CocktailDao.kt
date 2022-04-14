package com.hitme.android.mycocktailbar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktails ORDER BY name")
    fun getCocktails(): Flow<List<Cocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: Cocktail)

    @Delete
    suspend fun delete(cocktail: Cocktail)
}
