package com.android.segunfrancis.gdgph.ui.joinGDG

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.scaleAnimator
import com.google.android.material.button.MaterialButton

class JoinGDGFragment : Fragment() {

    private lateinit var joinGDGViewModel: JoinGDGViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        joinGDGViewModel =
            ViewModelProviders.of(this).get(JoinGDGViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_join_gdg, container, false)
        val meetupButton: MaterialButton = root.findViewById(R.id.button_meetup)
        scaleAnimator(meetupButton)
        val gdgUrl = "https://www.meetup.com/GDG-Port-Harcourt/"
        meetupButton.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(root.context, Uri.parse(gdgUrl))
        }
        joinGDGViewModel.text.observe(this, Observer {

        })
        return root
    }
}