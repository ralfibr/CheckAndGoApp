package com.androidcourse.checkgoapp.ui.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.ui.List.List
import com.androidcourse.checkgoapp.ui.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView

class Chat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        supportActionBar?.title = "Chat"
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.selectedItemId = R.id.navigation_chat
        navigationView.setOnNavigationItemSelectedListener() { item ->

            when (item.itemId) {
                R.id.navigation_profile ->
                {

                    navigetToProfile()
                }
                R.id.navigation_list ->
                {
                    navigetToList()
                }
                R.id.navigation_chat ->
                {}
            }
            true

        }
    }
    fun navigetToList() {
        startActivity(Intent(this, List::class.java))
    }
    fun navigetToProfile() {
        startActivity(Intent(this, Profile::class.java))
    }
}
