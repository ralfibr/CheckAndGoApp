package com.androidcourse.checkgoapp.ui.List

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.androidcourse.checkgoapp.database.ItemRepository
import com.androidcourse.checkgoapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.List

class ListActivityModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val itemRepository = ItemRepository(application.applicationContext)

    val items: LiveData<List<Item>> = itemRepository.getAllItems()

    fun insertItem(item: Item) {
        ioScope.launch {
            itemRepository.insertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        ioScope.launch {
           itemRepository.deleteItem(item)
        }
    }
fun deleteAll() {
    ioScope.launch {
        itemRepository.deleteAllItems()
    }
}
}


