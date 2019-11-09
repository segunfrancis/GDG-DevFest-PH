package com.android.segunfrancis.gdgph.ui.feedback

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.model.Feedback
import com.android.segunfrancis.gdgph.utility.MethodUtils
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackFragment : Fragment() {

    companion object {
        const val TAG = "FeedbackFragment"
    }

    private lateinit var feedbackViewModel: FeedbackViewModel
    private lateinit var planning: RatingBar
    private lateinit var venue: RatingBar
    private lateinit var timeManagement: RatingBar
    private lateinit var overall: RatingBar
    private lateinit var feedbackComplain: EditText
    private lateinit var submit: MaterialButton
    private lateinit var progress: ProgressBar
    private lateinit var mReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel =
            ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)
        //val textView: TextView = root.findViewById(R.id.text_slideshow)
        feedbackViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        mReference = FirebaseDatabase.getInstance().getReference("feedback")
        planning = root.findViewById(R.id.ratingBar_event_planning)
        venue = root.findViewById(R.id.ratingBar_event_venue)
        timeManagement = root.findViewById(R.id.ratingBar_time_management)
        overall = root.findViewById(R.id.ratingBar_overall_event_rating)
        feedbackComplain = root.findViewById(R.id.feedback_complains)
        submit = root.findViewById(R.id.send_feedback)
        progress = root.findViewById(R.id.progressBar_horizontal)

        submit.setOnClickListener {
            submit.text = "Sending..."
            submit.isEnabled = false
            disable()
            progress.visibility = View.VISIBLE
            val planningRating = planning.rating
            val venueRating = venue.rating
            val timeRating = timeManagement.rating
            val overallRating = overall.rating
            val comments = feedbackComplain.text.toString()

            val feedback = Feedback()
            feedback.planningRating = planningRating
            feedback.venueRating = venueRating
            feedback.timeRating = timeRating
            feedback.overallRating = overallRating
            feedback.comments = comments
            mReference.push().setValue(feedback).addOnSuccessListener {
                progress.visibility = View.INVISIBLE
                MethodUtils.showSnackBar("Thanks for your feedback")
            }.addOnFailureListener {
                progress.visibility = View.INVISIBLE
                MethodUtils.showSnackBar("Something went wrong")
                enable()
                submit.text = "Send Feedback"
                submit.isEnabled = true
                Log.e(TAG, "Error: ${it.localizedMessage}")
            }
        }
        return root
    }

    private fun disable() {
        planning.isEnabled = false
        venue.isEnabled = false
        timeManagement.isEnabled = false
        overall.isEnabled = false
        feedbackComplain.isEnabled = false
    }

    private fun enable() {
        planning.isEnabled = true
        venue.isEnabled = true
        timeManagement.isEnabled = true
        overall.isEnabled = true
        feedbackComplain.isEnabled = true
    }
}