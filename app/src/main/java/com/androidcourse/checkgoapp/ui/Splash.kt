package com.androidcourse.checkgoapp.ui
import com.androidcourse.checkgoapp.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

/**
 * Splash screen with 2500 seconds
 */

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.supportActionBar?.hide();
        Handler().postDelayed({
            startActivity(
                Intent(
                    this,
                    SignIn::class.java
                )
            ) // finish and remove from back stack
            finish()
        }, 2500)
    }

}

