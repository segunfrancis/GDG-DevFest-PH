package com.android.segunfrancis.gdgph.ui.organisers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.adapter.OrganisersAdapter
import com.android.segunfrancis.gdgph.model.Organisers
import com.google.firebase.database.*

class OrganisersFragment : Fragment() {

    private lateinit var organisersViewModel: OrganisersViewModel
    private lateinit var adapter: OrganisersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        organisersViewModel =
            ViewModelProviders.of(this).get(OrganisersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organisers, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.organisers_recycler_view)
        val pb = root.findViewById<ProgressBar>(R.id.organisers_progress_bar)
        recyclerView.layoutManager = GridLayoutManager(root.context, 2)

        organisersViewModel.recyclerView.observe(this, Observer {
            adapter = OrganisersAdapter(it, root.context)
            recyclerView.adapter = adapter
            pb.visibility = View.GONE
        })
        return root
    }
}