package com.androidcourse.checkgoapp.ui.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.model.Message

class ChatAdapter(val mCtx: Context, val layoutResId: Int,val  chatList: List<Message>):
    ArrayAdapter<Message> (mCtx,layoutResId,chatList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textViewName = view.findViewById<TextView>(R.id.tvMessageItem1)
        val texViewUserName = view.findViewById<TextView>(R.id.textViewUserName)
        val item = chatList[position]
        textViewName.text = item.message
        texViewUserName.text = item.username

        return view
    }
}