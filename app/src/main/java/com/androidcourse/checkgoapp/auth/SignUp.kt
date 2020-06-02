package com.androidcourse.checkgoapp.auth
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*



class SignUp : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var btnSignUp: Button? = null
    private var btnResetPassword: Button? = null
    private var progressBar: ProgressBar? = null
    private var inputName: EditText? = null
    private var auth : FirebaseAuth?= null
    private lateinit var database: DatabaseReference
    val randomValues1 = Random(10)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        this.supportActionBar?.hide();

        database = Firebase.database.reference
        //        add my code
        auth = FirebaseAuth.getInstance()

        btnSignIn = findViewById(R.id.sign_in_button2) as Button
        btnSignUp = findViewById(R.id.sign_up_button2) as Button
        inputEmail = findViewById(R.id.email3) as EditText
        inputPassword = findViewById(R.id.password3) as EditText
        progressBar = findViewById(R.id.progressBar2) as ProgressBar
        inputName = findViewById(R.id.name) as EditText

        btnSignIn!!.setOnClickListener(View.OnClickListener {
            finish()
        })
        btnSignUp!!.setOnClickListener(View.OnClickListener {
            database = FirebaseDatabase.getInstance().getReference("/users")
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()
            val username = inputName!!.text.toString().trim()
            if (TextUtils.isEmpty(email) ){
                Toast.makeText(applicationContext,"Enter your email Address!!", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext,"Enter your Password", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            if (password.length < 6){
                Toast.makeText(applicationContext,"Password too short, enter mimimum 6 charcters" , Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            progressBar!!.setVisibility(View.VISIBLE)

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            Log.d("MainActivity", "Email is: " + email)
            Log.d("MainActivity", "Password: $password")

            // Firebase Authentication to create a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(this,"User " + username.toString()+"is crated",
                    Toast.LENGTH_SHORT).show()
                   progressBar!!.setVisibility(View.VISIBLE)
                    startActivity(Intent(this, SignIn::class.java))
                    writeNewUser(username,email)
                        finish()
                    // else if successful
                    Log.d("Main", "Successfully created user with uid: ")
                }
                .addOnFailureListener{
                    Log.d("Main", "Failed to create user: ${it.message}")
                    Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            CoroutineScope(Dispatchers.Main).launch {

            }

        })

    }

    private fun writeNewUser( name: String, email: String?) {
        val user = User(name, email)
        database.child(auth?.currentUser?.uid.toString() + "/" ).setValue(user)


    }


}
