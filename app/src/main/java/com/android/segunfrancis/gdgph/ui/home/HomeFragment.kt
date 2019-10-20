package com.android.segunfrancis.gdgph.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.adapter.ActivitiesAdapter
const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mActivitiesAdapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)

        homeViewModel.recyclerView.observe(this, Observer {
            mActivitiesAdapter = ActivitiesAdapter(it, root.context)
            Log.d(TAG, "Activities: $it")
            recyclerView.adapter = mActivitiesAdapter
            progressBar.visibility = View.GONE
        })
        return root
    }
}