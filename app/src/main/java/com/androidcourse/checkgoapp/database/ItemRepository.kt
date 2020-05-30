package com.androidcourse.checkgoapp.database
import android.content.Context
import com.androidcourse.checkgoapp.model.Item

class ItemRepository(context: Context) {

    private val itemDao: ItemDao
    init {
       val database = CheckListRoomDatabase.getDatabase(context)
        itemDao = database!!.itemDao()
    }

    suspend fun getAllItems(): List<Item> = itemDao.getAllItems()

    suspend fun insertItem(item: Item) = itemDao.insertItem(item)
    suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)
    suspend fun updateNotepad(item: Item) {
       itemDao.updateItem(item)
    }
    suspend fun deleteAllItems() = itemDao.deleteAllitems()

}
