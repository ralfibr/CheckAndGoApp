package com.androidcourse.checkgoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.adapter.ItemAdapter
import com.androidcourse.checkgoapp.database.ItemRepository
import com.androidcourse.checkgoapp.model.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class List : AppCompatActivity() {
    private val checkList = arrayListOf<Item>()
    private val itemAdapter = ItemAdapter(checkList)
    private lateinit var itemRepository: ItemRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.title = "Your checklist"

        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_list ->
                {Toast.makeText(application,"clickked",Toast.LENGTH_SHORT).show()
              }
                R.id.navigation_chat ->
                    Toast.makeText(application,"clickked chat",Toast.LENGTH_SHORT).show()
                R.id.navigation_profile ->
                {
                    navigateToList() }
            }
            true

        }
    }
//    private fun initViews() {
//        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        rvItems.adapter = itemAdapter
//        rvItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        createItemTouchHelper().attachToRecyclerView(rvItems)
//        getShoppingListFromDatabase()
//
//        floatingActionButton.setOnClickListener {  }
//    }
    fun navigateToList() {
        startActivity(Intent(this, Profile::class.java))

    }
    private fun initViews() {
//        floatingActionButton.setOnClickListener {
//            startActivity(Intent(this, AddList::class.java))
//
//        }
    }

}
