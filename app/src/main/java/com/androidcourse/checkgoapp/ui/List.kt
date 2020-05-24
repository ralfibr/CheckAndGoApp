package com.androidcourse.checkgoapp.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.adapter.ItemAdapter
import com.androidcourse.checkgoapp.database.ItemRepository
import com.androidcourse.checkgoapp.model.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private val checkList = arrayListOf<Item>()
private val itemAdapter = ItemAdapter(checkList)
private lateinit var itemRepository: ItemRepository
private val mainScope = CoroutineScope(Dispatchers.Main)
private var inputItem: EditText? = null



class List : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.title = "Your checklist"
        initViews()

        itemRepository = ItemRepository(this)

        inputItem = findViewById(R.id.itemEdit)
        deleteBtn.setOnClickListener(View.OnClickListener { deleteCheckList() })
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_list -> {
                    Toast.makeText(application, "clickked", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_chat ->
                    Toast.makeText(application, "clickked chat", Toast.LENGTH_SHORT).show()
                R.id.navigation_profile -> {
                    navigateToList()
                }
            }
            true

        }
    }

    private fun initViews() {
        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvItems.adapter = itemAdapter
        rvItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvItems)
        getCheckListFromDatabase()
        floatingActionButton.setOnClickListener { addItem() }
    }
    fun navigateToList() {
        startActivity(Intent(this, Profile::class.java))

    }

    private fun addItem() {
        val itemName = inputItem!!.text.toString().trim()
        if (itemName.isEmpty()){
            Toast.makeText(
                this, "Item cannot be empty",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        mainScope.launch {
            Log.d("MainActivity", itemName)
            val item = Item(name = itemName)
            withContext(Dispatchers.IO) {
                itemRepository.insertItem(item)
            }

            getCheckListFromDatabase()
           inputItem!!.setText("")
        }

    }


    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = checkList[position]
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        itemRepository.deleteItem(itemToDelete)
                    }
                    getCheckListFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    // add product to the database
    private fun getCheckListFromDatabase() {
        mainScope.launch {
            // call product repository
            val chcekList = withContext(Dispatchers.IO) {
                itemRepository.getAllItems()
            }
            checkList.clear()
            checkList.addAll(chcekList)
            itemAdapter.notifyDataSetChanged()
        }
    }


    private fun deleteCheckList() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                itemRepository.deleteAllItems()
            }
            getCheckListFromDatabase()
            Snackbar.make(
                rvItems
                , // Parent view
                "All items is deleted", // Message to show
                Snackbar.LENGTH_SHORT // How long to display the message.
            ).show()
        }
    }


}
