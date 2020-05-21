package com.androidcourse.checkgoapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidcourse.checkgoapp.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment()  {

    private lateinit var profileViewModel: ProfileViewModel
    private var auth: FirebaseAuth?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        return root
        auth = FirebaseAuth.getInstance()
    }
    private fun onSignOut() {
        auth?.signOut()

    }
}
