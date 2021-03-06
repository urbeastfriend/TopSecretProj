package com.mvvm.fithelperapp.data.Recipes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes_table ORDER BY name")
    fun getRecipes() : Flow<List<Recipe>>
    @Query("SELECT * FROM recipes_table ORDER BY name")
    fun getRecipesList() : List<Recipe>

    @Query("SELECT * FROM recipes_table WHERE category = :categoryName")
    fun getRecipesByCategory(categoryName: String) : Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)




}