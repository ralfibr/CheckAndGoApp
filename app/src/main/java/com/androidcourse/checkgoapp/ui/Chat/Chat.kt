package com.androidcourse.checkgoapp.ui.Chat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.model.Message
import com.androidcourse.checkgoapp.ui.List.List
import com.androidcourse.checkgoapp.ui.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

/**
 * @author Raeef Ibrahim
 * Check&Go App
 *
 */
class Chat : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var auth: FirebaseAuth? = null
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

        getMessages()//get all massages

        // TabBar
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
        sendBtn.setOnClickListener {
            wirteMassageToFirebase(
                massage!!.text.toString().trim(),
                Calendar.getInstance().time.toString()
            )
        }
        deleteBtn2.setOnClickListener(View.OnClickListener { deleteMessges() })
    }

    fun navigetToList() {
        startActivity(Intent(this, List::class.java))
    }

    fun navigetToProfile() {
        startActivity(Intent(this, Profile::class.java))
    }

    //Send massage and saze to Firebase
    fun wirteMassageToFirebase(message: String, date: String) {

        val messageId = database.push().key
        val messageO = Message(
            messageId.toString(),
            message,
            auth?.currentUser?.email.toString().substring(0, 5),
            date
        )
        massage!!.text = null
        database.child(messageId.toString()).setValue(messageO)
        getMessages()


    }

    // Delete aal mesagess
    fun deleteMessges() {
        database.removeValue()
        getMessages()
        startActivity(Intent(this, Chat::class.java))
        Toast.makeText(applicationContext, "All chat messages are deleted!", Toast.LENGTH_SHORT)
            .show()
    }

    // Get all messages
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
