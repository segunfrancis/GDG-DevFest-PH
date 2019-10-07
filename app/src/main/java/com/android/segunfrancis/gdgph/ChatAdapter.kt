package com.android.segunfrancis.gdgph

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private var mContext: Context
    private var mMessageList: List<Chat>
    private var mAuth: FirebaseUser?

    constructor(mContext: Context, mMessageList: List<Chat>) : super() {
        this.mContext = mContext
        this.mMessageList = mMessageList
        mAuth = FirebaseAuth.getInstance().currentUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.message_list, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mMessageList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        // TODO: Check
        holder.bind(mMessageList[position])

        // User's text message
        if (mMessageList[position].msgUser == "user") {
            holder.rightText.text = mMessageList[position].msgText
            holder.usernameText.text = mAuth?.displayName
            //Log.d("ChatAdapter", "Display Name: ${mAuth?.displayName}")
            holder.rightTextLayout.visibility = View.VISIBLE
            holder.leftTextLayout.visibility = View.GONE
        } else {
            // Bot's text message
            holder.leftText.text = mMessageList[position].msgText
            holder.rightTextLayout.visibility = View.GONE
            holder.leftTextLayout.visibility = View.VISIBLE
        }
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var usernameText: TextView
        lateinit var leftText: TextView
        lateinit var rightText: TextView
        lateinit var leftTextLayout: LinearLayout
        lateinit var rightTextLayout: LinearLayout

        fun bind(item: Chat) = with(itemView) {
            usernameText = itemView.findViewById(R.id.username)
            leftText = itemView.findViewById(R.id.left_text)
            rightText = itemView.findViewById(R.id.right_text)
            leftTextLayout = itemView.findViewById(R.id.left_text_layout)
            rightTextLayout = itemView.findViewById(R.id.right_text_layout)
        }
    }
}