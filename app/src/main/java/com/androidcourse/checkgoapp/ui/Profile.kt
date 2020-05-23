package com.androidcourse.checkgoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.androidcourse.checkgoapp.SignIn
import com.androidcourse.checkgoapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile.*

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
        startActivity(Intent(this, SignIn::class.java))
    }
fun navigetToList() {
    startActivity(Intent(this, List::class.java))
}
    fun getProfile(): String {
return auth?.currentUser?.email.toString()

    }
}
