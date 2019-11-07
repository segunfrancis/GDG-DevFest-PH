package com.android.segunfrancis.gdgph.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.segunfrancis.gdgph.FeedbackActivity
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.model.Activities
import com.android.segunfrancis.gdgph.utility.MethodUtils
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.makeramen.roundedimageview.RoundedImageView

class ActivitiesAdapter : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder> {

    private var mActivitiesList: List<Activities>
    private var mContext: Context

    constructor(data: List<Activities>, mContext: Context) : super() {
        this.mActivitiesList = data
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        return ActivitiesViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.activities_list, parent, false)
        )
    }

    override fun getItemCount() = mActivitiesList.size

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        holder.bind(mActivitiesList[position])

        holder.topicTextView.text = mActivitiesList[position].topic
        holder.speakerNameTextView.text = mActivitiesList[position].speakerName
        if (mActivitiesList[position].difficulty == "") {
            holder.difficultyTextView.visibility = View.GONE
        } else {
            holder.difficultyTextView.text = mActivitiesList[position].difficulty
        }
        holder.descriptionTextView.text = mActivitiesList[position].description
        holder.timeTextView.text = mActivitiesList[position].time
        holder.timeConversationTextView.text = mActivitiesList[position].timeConversation
        if (mActivitiesList[position].track == "") {
            holder.trackTextView.visibility = View.GONE
        } else {
            holder.trackTextView.text = mActivitiesList[position].track
        }
        if (mActivitiesList[position].photoUrl != "") {
            Glide.with(mContext.applicationContext)
                .load(mActivitiesList[position].photoUrl)
                .placeholder(R.drawable.ic_person_dark)
                .into(holder.speakerPhoto)
        } else {
            Glide.with(mContext.applicationContext)
                .load("")
                .into(holder.speakerPhoto)
        }
        if (mActivitiesList[position].feedback != "") {
            holder.feedbackTextView.visibility = View.VISIBLE
            holder.feedbackTextView.text = "Feedback"
        } else {
            holder.feedbackTextView.visibility = View.INVISIBLE
        }
    }

    class ActivitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var topicTextView: TextView
        lateinit var speakerNameTextView: TextView
        lateinit var difficultyTextView: TextView
        lateinit var descriptionTextView: TextView
        lateinit var timeTextView: TextView
        lateinit var timeConversationTextView: TextView
        lateinit var trackTextView: TextView
        lateinit var feedbackTextView: TextView
        lateinit var speakerPhoto: RoundedImageView
        private lateinit var auth: FirebaseAuth

        fun bind(item: Activities) = with(itemView) {
            topicTextView = findViewById(R.id.tv_topic)
            speakerNameTextView = findViewById(R.id.tv_speaker)
            difficultyTextView = findViewById(R.id.tv_difficulty)
            descriptionTextView = findViewById(R.id.tv_description)
            timeTextView = findViewById(R.id.tv_time)
            timeConversationTextView = findViewById(R.id.tv_time_conversation)
            trackTextView = findViewById(R.id.tv_track)
            feedbackTextView = findViewById(R.id.tv_feedback)
            speakerPhoto = findViewById(R.id.speakerPhoto)
            auth = FirebaseAuth.getInstance()

            feedbackTextView.setOnClickListener {
                if (auth.currentUser != null) {
                    val intent = Intent(it.context.applicationContext, FeedbackActivity::class.java)
                    it.context.startActivity(intent)
                } else {
                    MethodUtils.showSnackBar("Sign in to give feedback")
                }
            }
        }
    }
}