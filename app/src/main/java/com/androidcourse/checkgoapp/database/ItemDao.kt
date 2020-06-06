package com.androidcourse.checkgoapp.database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.androidcourse.checkgoapp.model.Item

/**
 * @author Raeef Ibrahim
 * Check&Go App
 *
 */
@Dao
interface ItemDao {

    @Query("SELECT * FROM item_table")
     fun getAllItems(): LiveData<List<Item>>

    @Insert
     fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
    @Update
    suspend fun updateItem(item: Item)
    @Query("DELETE FROM item_table")
    suspend fun deleteAllitems() }