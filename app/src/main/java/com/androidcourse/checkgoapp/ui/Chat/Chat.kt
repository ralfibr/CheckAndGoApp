package com.androidcourse.checkgoapp.ui.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.model.Item
import com.androidcourse.checkgoapp.model.Message
import com.androidcourse.checkgoapp.model.RandomEnum
import com.androidcourse.checkgoapp.model.User
import com.androidcourse.checkgoapp.ui.List.List
import com.androidcourse.checkgoapp.ui.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.profile.*
import kotlin.random.Random

class Chat : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var auth: FirebaseAuth? = null
    private var id: Int? = null
    private lateinit var listview: ListView
    private lateinit var chatList: MutableList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chatList = mutableListOf()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("/Chats")
        supportActionBar?.title = "Chat"
        listview = findViewById(R.id.chatsListView)
        getMessages()
        val navigationView = findViewById(R.id.bottom_nav) as BottomNavigationView
        navigationView.selectedItemId = R.id.navigation_chat
        navigationView.setOnNavigationItemSelectedListener() { item ->

            when (item.itemId) {
                R.id.navigation_profile -> {

                    navigetToProfile()
                }
                R.id.navigation_list -> {
                    navigetToList()
                }
                R.id.navigation_chat -> {
                }
            }
            true

        }
        sendBtn.setOnClickListener { wirteMassageToFirebase(massage!!.text.toString().trim()) }
        deleteBtn2.setOnClickListener(View.OnClickListener { deleteMessges() })
    }

    fun navigetToList() {
        startActivity(Intent(this, List::class.java))
    }

    fun navigetToProfile() {
        startActivity(Intent(this, Profile::class.java))
    }

    fun wirteMassageToFirebase(message: String) {

//        database.child(auth?.currentUser?.uid.toString() + "/" )

        val messageId = database.push().key
        val messageO = Message(messageId.toString(), message, auth?.currentUser?.email.toString().substring(0,5))
        massage!!.text = null
        database.child(messageId.toString()).setValue(messageO)
        getMessages()


    }

    fun deleteMessges() {
        database.removeValue()
        getMessages()
        startActivity(Intent(this, Chat::class.java))
    }

    fun getMessages() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    chatList.clear()
                    for (h in p0.children) {
                        val messageItem = h.getValue(Message::class.java)
                        chatList.add(messageItem!!)

                    }
                    val adapter = ChatAdapter(applicationContext, R.layout.message_item, chatList)
                    listview.adapter = adapter
                    adapter.notifyDataSetChanged()
                }


                return
            }

        })




    }
}
