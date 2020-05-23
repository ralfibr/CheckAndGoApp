package com.androidcourse.checkgoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class List : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


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
