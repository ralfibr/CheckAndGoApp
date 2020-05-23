package com.androidcourse.checkgoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile.*
import kotlin.collections.List

class Profile : AppCompatActivity() {
    private var auth : FirebaseAuth?= null
    private var signOut: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        signOut = findViewById(R.id.signOut) as ImageButton
        signOut!!.setOnClickListener(View.OnClickListener { signOut() })
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        auth = FirebaseAuth.getInstance()
        email_Textview.text = getString(R.string.username,getProfile())
        navigationView.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_profile ->
                {
                    Toast.makeText(application,"clickked", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_list ->
                {
                    navigetToList()
                }
                R.id.navigation_chat ->
                    Toast.makeText(application,"clickked chat", Toast.LENGTH_SHORT).show()

            }
            true

        }
    }
    fun signOut() {
        auth?.signOut()
        Toast.makeText(application,"You are logged out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }
fun navigetToList() {
    startActivity(Intent(this, com.androidcourse.checkgoapp.List::class.java))
}
    fun getProfile(): String {
return auth?.currentUser?.email.toString()

    }
}
