package com.gdg.segunfrancis.gdgph.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdg.segunfrancis.gdgph.R
import com.gdg.segunfrancis.gdgph.SpeakerDetails
import com.gdg.segunfrancis.gdgph.model.Speakers
import com.gdg.segunfrancis.gdgph.utility.MethodUtils.Companion.INTENT_KEY
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

class SpeakersAdapter : RecyclerView.Adapter<SpeakersAdapter.SpeakersViewHolder> {

    private var mContext: Context
    private var mSpeakersList: List<Speakers>

    constructor(context: Context, speakersList: List<Speakers>) : super() {
        mContext = context
        mSpeakersList = speakersList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakersViewHolder {
        return SpeakersViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.organisers_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mSpeakersList.size

    override fun onBindViewHolder(holder: SpeakersViewHolder, position: Int) {
        holder.bind(mSpeakersList[position])

        holder.speakerName.text = mSpeakersList[position].fullName
        holder.speakerTag.text = mSpeakersList[position].tagLine
        Glide.with(mContext)
            .load(mSpeakersList[position].profilePicture)
            .placeholder(R.drawable.avatar)
            .into(holder.speakerImage)
    }

    class SpeakersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var speakerName: TextView
        lateinit var speakerTag: TextView
        lateinit var speakerImage: RoundedImageView

        fun bind(items: Speakers) {
            speakerName = itemView.findViewById(R.id.organiser_name)
            speakerTag = itemView.findViewById(R.id.organiser_community)
            speakerImage = itemView.findViewById(R.id.organiser_image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SpeakerDetails::class.java)
                intent.putExtra(INTENT_KEY, items)
                itemView.context.startActivity(intent)
            }
        }
    }
}