package com.android.segunfrancis.gdgph.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.utility.MethodUtils
import com.makeramen.roundedimageview.RoundedImageView

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var myProfileImage: RoundedImageView
    private lateinit var gmailIcon: RoundedImageView
    private lateinit var linkedInIcon: RoundedImageView
    private lateinit var twitterIcon: RoundedImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about_developer, container, false)
        myProfileImage = root.findViewById(R.id.my_profile_image)
        gmailIcon = root.findViewById(R.id.gmail_image)
        linkedInIcon = root.findViewById(R.id.linkedin_image)
        twitterIcon = root.findViewById(R.id.twitter_image)

        MethodUtils.loadImage(root.context, R.drawable.profile_photo, myProfileImage)
        MethodUtils.loadImage(root.context, R.drawable.ic_gmail, gmailIcon)
        MethodUtils.loadImage(root.context, R.drawable.ic_linkedin, linkedInIcon)
        MethodUtils.loadImage(root.context, R.drawable.ic_twitter, twitterIcon)

        aboutViewModel.text.observe(this, Observer {

        })
        return root
    }
}