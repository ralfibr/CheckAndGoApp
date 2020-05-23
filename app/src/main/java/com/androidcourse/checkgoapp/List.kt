package com.androidcourse.checkgoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.profile.*

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

}
