package com.androidcourse.checkgoapp.database
import androidx.room.*
import com.androidcourse.checkgoapp.model.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM item_table")
    suspend fun getAllItems(): List<Item>

    @Insert
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
    @Update
    suspend fun updateItem(item: Item)
    @Query("DELETE FROM item_table")
    suspend fun deleteAllitems() }