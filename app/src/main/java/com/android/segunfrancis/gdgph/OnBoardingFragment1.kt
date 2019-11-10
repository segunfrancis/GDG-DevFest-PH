package com.android.segunfrancis.gdgph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class OnBoardingFragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_1, container, false)
        val backgroundImage = view.findViewById<ImageView>(R.id.background_image)
        Glide.with(view.context)
            .load(R.drawable.background_ph)
            .into(backgroundImage)
        return view
    }

    companion object {
        fun newInstance() = OnBoardingFragment1()
    }
}