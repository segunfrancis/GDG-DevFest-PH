package com.android.segunfrancis.gdgph

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class OnBoardingFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_2, container, false)
        val nextActivityButton = view.findViewById<ImageView>(R.id.next_activity_image)
        nextActivityButton.setOnClickListener {
            nextActivity()
        }
        return view
    }

    private fun nextActivity() {
        val intent = Intent(context, DetailsActivity::class.java)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        activity?.finish()
    }

    companion object {
        fun newInstance() = OnBoardingFragment2()
    }
}