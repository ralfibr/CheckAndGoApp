package com.androidcourse.checkgoapp.database
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidcourse.checkgoapp.model.Item

class ItemRepository(context: Context) {

    private val itemDao: ItemDao

    init {
        val database = CheckListRoomDatabase.getDatabase(context)
        itemDao = database!!.itemDao()
    }

    fun getAllItems(): LiveData<List<Item>> {
        return itemDao?.getAllItems() ?: MutableLiveData(emptyList())
    }


     fun insertItem(item: Item) = itemDao.insertItem(item)
    suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)
    suspend fun updateNotepad(item: Item) {
       itemDao.updateItem(item)
    }
    suspend fun deleteAllItems() = itemDao.deleteAllitems()

}
