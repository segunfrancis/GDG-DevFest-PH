package com.android.segunfrancis.gdgph.ui.about

import android.content.Intent
import android.net.Uri
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
    private lateinit var whatsappIcon: RoundedImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about_developer, container, false)
        myProfileImage = root.findViewById(R.id.my_profile_image)
        gmailIcon = root.findViewById(R.id.gmail_image)
        linkedInIcon = root.findViewById(R.id.linkedin_image)
        twitterIcon = root.findViewById(R.id.twitter_image)
        whatsappIcon = root.findViewById(R.id.whatsapp_image)

        MethodUtils.loadImage(root.context, R.drawable.profile_photo, myProfileImage)
        MethodUtils.loadImage(root.context, R.drawable.ic_gmail, gmailIcon)
        MethodUtils.loadImage(root.context, R.drawable.ic_linkedin, linkedInIcon)
        MethodUtils.loadImage(root.context, R.drawable.ic_twitter, twitterIcon)
        MethodUtils.loadImage(root.context, R.drawable.ic_whatsapps, whatsappIcon)

        gmailIcon.setOnClickListener {
            val myEmailAddress = "mailto:francis4dblues@gmail.com"
            val emailAddressUri = Uri.parse(myEmailAddress)
            val gmailIntent = Intent(Intent.ACTION_SENDTO, emailAddressUri)
            startActivity(Intent.createChooser(gmailIntent, "Select Email Application"))
        }

        linkedInIcon.setOnClickListener {
            val linkedInAddress = "https://www.linkedin.com/in/segun-francis-302361a1"
            val linkedInUri = Uri.parse(linkedInAddress)
            val twitterIntent = Intent(Intent.ACTION_VIEW, linkedInUri)
            startActivity(Intent.createChooser(twitterIntent, "Select Application"))
        }

        whatsappIcon.setOnClickListener {
            val whatsappAddress = "https://api.whatsapp.com/send?phone=2348076840302"
            val whatsappUri = Uri.parse(whatsappAddress)
            val twitterIntent = Intent(Intent.ACTION_VIEW, whatsappUri)
            startActivity(Intent.createChooser(twitterIntent, "Select Application"))
        }

        twitterIcon.setOnClickListener {
            val twitterAddress = "https://www.twitter.com/segun__francis"
            val twitterUri = Uri.parse(twitterAddress)
            val twitterIntent = Intent(Intent.ACTION_VIEW, twitterUri)
            startActivity(Intent.createChooser(twitterIntent, "Select Application"))
        }

        aboutViewModel.text.observe(this, Observer {

        })
        return root
    }
}