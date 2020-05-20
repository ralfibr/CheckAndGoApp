package com.androidcourse.checkgoapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var btnSignUp: Button? = null
    private var btnResetPassword: Button? = null
    private var progressBar: ProgressBar? = null

    private var auth : FirebaseAuth?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        this.supportActionBar?.hide();


        //        add my code
        auth = FirebaseAuth.getInstance()

        btnSignIn = findViewById(R.id.sign_in_button2) as Button
        btnSignUp = findViewById(R.id.sign_up_button2) as Button
        inputEmail = findViewById(R.id.email3) as EditText
        inputPassword = findViewById(R.id.password3) as EditText
        progressBar = findViewById(R.id.progressBar2) as ProgressBar
        //btnResetPassword = findViewById(R.id.btn_reset_password) as Button
//
//        btnResetPassword!!.setOnClickListener(View.OnClickListener {
//           // startActivity(Intent(this@SingupActivity, ResetPasswordActivity::class.java))
//        })

        btnSignIn!!.setOnClickListener(View.OnClickListener {
            finish()
        })
        btnSignUp!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()

            if (TextUtils.isEmpty(email)){
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

            //create user
            auth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, OnCompleteListener {
                        task ->
                    Toast.makeText(this,"User is crated",
                        Toast.LENGTH_SHORT).show()
                    progressBar!!.setVisibility(View.VISIBLE)

                    if (!task.isSuccessful){
                        Toast.makeText(this,"User Not crated", Toast.LENGTH_SHORT).show()
                        return@OnCompleteListener
                    }else{
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }


                })

        })
    }


}
