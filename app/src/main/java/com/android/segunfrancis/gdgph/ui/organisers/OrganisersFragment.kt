package com.android.segunfrancis.gdgph.ui.organisers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R

class OrganisersFragment : Fragment() {

    private lateinit var organisersViewModel: OrganisersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        organisersViewModel =
            ViewModelProviders.of(this).get(OrganisersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organisers, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        organisersViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}