package com.android.segunfrancis.gdgph

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ActivitiesAdapter : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder> {

    private var activitiesList: List<Activities>
    private var mContext: Context

    constructor(data: List<Activities>, mContext: Context) : super() {
        this.activitiesList = data
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        return ActivitiesViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.activities_list, parent, false)
        )
    }

    override fun getItemCount() = activitiesList.size

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) =
        holder.bind(activitiesList[position])

    class ActivitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Activities) = with(itemView) {
            // TODO: Bind the activitiesList with View
            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}