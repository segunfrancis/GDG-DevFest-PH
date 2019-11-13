package com.gdg.segunfrancis.gdgph.ui.speakers

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
import com.gdg.segunfrancis.gdgph.R
import com.gdg.segunfrancis.gdgph.adapter.SpeakersAdapter

class SpeakersFragment : Fragment() {

    private lateinit var speakersViewModel: SpeakersViewModel
    private lateinit var mAdapter: SpeakersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        speakersViewModel =
            ViewModelProviders.of(this).get(SpeakersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_speakers, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.speakers_recycler_view)
        val pb: ProgressBar = root.findViewById(R.id.speakers_progress_bar)
        pb.visibility = View.VISIBLE
        recyclerView.layoutManager =
            GridLayoutManager(root.context, 2, GridLayoutManager.VERTICAL, false)
        speakersViewModel.text.observe(this, Observer {
            mAdapter = SpeakersAdapter(root.context, it)
            recyclerView.adapter = mAdapter
            pb.visibility = View.GONE
        })
        return root
    }
}