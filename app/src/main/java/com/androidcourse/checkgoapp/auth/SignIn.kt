package com.androidcourse.checkgoapp.auth
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.ui.List.List
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/**
 * @author Raeef Ibrahim
 * Check&Go App
 * SignIn
 *
*/
class SignIn : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var btnSignup: Button? = null
    private var btnLogin: Button? = null
    private var btnReset: Button? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRef = Firebase.database.reference // get firrebase instance
        auth = FirebaseAuth.getInstance() // get auth firebase instance
        this.supportActionBar?.hide();
        inputEmail = findViewById(R.id.email) as EditText
        inputPassword = findViewById(R.id.password) as EditText
        btnSignup = findViewById(R.id.btn_signup) as Button
        btnLogin = findViewById(R.id.btn_login) as Button
        progressBar = findViewById(R.id.progressBar) as ProgressBar
        progressBar!!.setVisibility(View.INVISIBLE)

// Set signUp button on click
        btnSignup!!.setOnClickListener(View.OnClickListener {
            onSignup()
        })
// Set Sign in button on click
        btnLogin!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please Entre your email.", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Please Enter your Password", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            progressBar!!.setVisibility(View.VISIBLE)
          onSignIn(email,password)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



    /**
     * @param email
     * @param password
     * Sign in with firebase
     */
    fun onSignIn(email: String,password: String){

    auth!!.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, OnCompleteListener { task ->
            progressBar!!.setVisibility(View.VISIBLE)
            if (!task.isSuccessful) {
                if (password.length < 6) {
                    inputPassword!!.setError(getString(R.string.minimum_password))
                } else {
                    Toast.makeText(
                        this, getString(R.string.auth_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                navigateToHome()
                finish()
            }
        })
}
    fun navigateToHome() {
        startActivity(Intent(this, List::class.java))
    }

    fun onSignup() {
        startActivity(Intent(this, SignUp::class.java))
    }

    override fun onStart() {
        super.onStart()
        if (auth?.currentUser == null) {
            return
        } else {
            val intent = Intent(this, List::class.java)
            startActivity(intent)
        }
    }

}

