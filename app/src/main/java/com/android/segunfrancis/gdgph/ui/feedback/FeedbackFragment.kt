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
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.R
import com.android.segunfrancis.gdgph.model.Feedback
import com.android.segunfrancis.gdgph.utility.MethodUtils
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackFragment : Fragment() {

    companion object {
        const val TAG = "FeedbackFragment"
    }

    private lateinit var feedbackViewModel: FeedbackViewModel
    private lateinit var refreshment: RatingBar
    private lateinit var venue: RatingBar
    private lateinit var timeManagement: RatingBar
    private lateinit var overall: RatingBar
    private lateinit var feedbackComplain: EditText
    private lateinit var submit: MaterialButton
    private lateinit var progress: ProgressBar
    private lateinit var mReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

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
        mAuth = FirebaseAuth.getInstance()
        mReference = FirebaseDatabase.getInstance().getReference("feedback")
        refreshment = root.findViewById(R.id.ratingBar_refreshment)
        venue = root.findViewById(R.id.ratingBar_event_venue)
        timeManagement = root.findViewById(R.id.ratingBar_time_management)
        overall = root.findViewById(R.id.ratingBar_overall_event_rating)
        feedbackComplain = root.findViewById(R.id.feedback_complains)
        submit = root.findViewById(R.id.send_feedback)
        progress = root.findViewById(R.id.progressBar_horizontal)

        if (savedInstanceState != null) {
            refreshment.rating = savedInstanceState.getFloat("refreshment")
            venue.rating = savedInstanceState.getFloat("venue")
            timeManagement.rating = savedInstanceState.getFloat("time")
            overall.rating = savedInstanceState.getFloat("overall")
            feedbackComplain.setText(savedInstanceState.getString("comment"))
        }

        submit.setOnClickListener {
            if (mAuth.currentUser == null) {
                MethodUtils.showSnackBar("Sign in to send feedback")
            } else {
                if (refreshment.rating == 0f || venue.rating == 0f || timeManagement.rating == 0f) {
                    MethodUtils.showSnackBar("A rating of 0 is not allowed")
                } else {
                    submit.text = "Sending..."
                    submit.isEnabled = false
                    disable()
                    progress.visibility = View.VISIBLE
                    val refreshmentRating = refreshment.rating
                    val venueRating = venue.rating
                    val timeRating = timeManagement.rating
                    val overallRating = overall.rating
                    val comments = feedbackComplain.text.toString()

                    val feedback = Feedback()
                    feedback.refreshmentRating = refreshmentRating
                    feedback.venueRating = venueRating
                    feedback.timeRating = timeRating
                    feedback.overallRating = overallRating
                    feedback.comments = comments
                    mReference.push().setValue(feedback).addOnSuccessListener {
                        progress.visibility = View.INVISIBLE
                        MethodUtils.showSnackBar("Thanks for your feedback")
                        clear()
                    }.addOnFailureListener {
                        progress.visibility = View.INVISIBLE
                        MethodUtils.showSnackBar("Something went wrong")
                        enable()
                        submit.text = "Send Feedback"
                        submit.isEnabled = true
                        Log.e(TAG, "Error: ${it.localizedMessage}")
                    }
                }
            }
        }
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat("refreshment", refreshment.rating)
        outState.putFloat("venue", venue.rating)
        outState.putFloat("time", timeManagement.rating)
        outState.putFloat("overall", overall.rating)
        outState.putString("comment", feedbackComplain.text.toString())
        super.onSaveInstanceState(outState)
    }

    private fun disable() {
        refreshment.isEnabled = false
        venue.isEnabled = false
        timeManagement.isEnabled = false
        overall.isEnabled = false
        feedbackComplain.isEnabled = false
    }

    private fun enable() {
        refreshment.isEnabled = true
        venue.isEnabled = true
        timeManagement.isEnabled = true
        overall.isEnabled = true
        feedbackComplain.isEnabled = true
    }

    private fun clear() {
        refreshment.rating = 0f
        venue.rating = 0f
        timeManagement.rating = 0f
        overall.rating = 0f
        feedbackComplain.isEnabled = true
        feedbackComplain.text.clear()
        submit.isEnabled = true
        submit.text = "Send Feedback"
        val ft: FragmentTransaction? = fragmentManager?.beginTransaction()
        ft?.detach(this)?.attach(this)?.commit()
    }
}