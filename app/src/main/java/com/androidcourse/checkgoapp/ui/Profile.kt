package com.androidcourse.checkgoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.auth.SignIn
import com.androidcourse.checkgoapp.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile.*

class Profile : AppCompatActivity() {
    private var auth : FirebaseAuth?= null
    private var signOut: ImageButton? = null
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        //tvUsername?.text = getString(R.string.uName,User.username.toString())
        database = FirebaseDatabase.getInstance().getReference("/users")
        signOut = findViewById(R.id.signOut) as ImageButton
        getDataFromFireBase()
        signOut!!.setOnClickListener(View.OnClickListener { signOut() })
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        auth = FirebaseAuth.getInstance()
        email_Textview.text = getString(R.string.username,getProfile())
        navigationView.selectedItemId = R.id.navigation_profile
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
    fun getDataFromFireBase(){
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

           // @SuppressLint("LongLogTag")
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val users = it.getValue(User::class.java)

                    if (users != null) {
                        tvUsername?.text = getString(R.string.uName,users.username.toString())
                       // Log.d("jfnrjkrbnfhjbrhjfbih3bghjrbghjrb3jhgbhjr3bgr34jgbhj", users.username.toString())
                    }
                    return
                }
            }

        })

    }
fun navigetToList() {
    startActivity(Intent(this, List::class.java))
}
    fun getProfile(): String {
return auth?.currentUser?.email.toString()

    }
}
