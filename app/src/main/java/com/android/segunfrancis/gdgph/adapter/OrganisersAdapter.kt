package com.android.segunfrancis.gdgph.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.model.Organisers
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

class OrganisersAdapter: RecyclerView.Adapter<OrganisersAdapter.OrganisersViewHolder> {

    private var mOrganisersList: List<Organisers>
    private var mContext: Context

    constructor(data: List<Organisers>, context: Context) : super() {
        mContext = context
        mOrganisersList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisersViewHolder {
        return OrganisersViewHolder(LayoutInflater.from(mContext).inflate(R.layout.organisers_list, parent, false))
    }

    override fun getItemCount(): Int {
       return mOrganisersList.size
    }

    override fun onBindViewHolder(holder: OrganisersViewHolder, position: Int) {
        holder.bind(mOrganisersList[position])

        holder.organiserName.text = mOrganisersList[position].name
        holder.organiserCommunity.text = mOrganisersList[position].community
        Glide.with(mContext)
            .load(mOrganisersList[position].imageUrl)
            .placeholder(R.drawable.avatar)
            .into(holder.organiserImage)
    }

    class OrganisersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var organiserName: TextView
        lateinit var organiserCommunity: TextView
        lateinit var organiserImage: RoundedImageView

        fun bind(items: Organisers) = with(itemView) {
            organiserName = findViewById(R.id.organiser_name)
            organiserCommunity = findViewById(R.id.organiser_community)
            organiserImage = findViewById(R.id.organiser_image)
        }
    }
}