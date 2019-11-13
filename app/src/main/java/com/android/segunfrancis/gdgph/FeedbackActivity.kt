package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.appcompat.widget.Toolbar
import com.android.segunfrancis.gdgph.model.SpeakerFeedback
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackActivity : AppCompatActivity() {

    private lateinit var mProgressBar: ProgressBar
    private lateinit var clarity: RatingBar
    private lateinit var knowledgeOfTopic: RatingBar
    private lateinit var overall: RatingBar
    private lateinit var feedbackComplains: EditText
    private lateinit var sendFeedbackButton: MaterialButton
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val intent = intent
        val speakerName = intent.getStringExtra("speaker_name")
        Log.d(TAG, "clickedPosition: $speakerName")

        // Hide Keyboard
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        clarity = findViewById(R.id.ratingBar_clarity)
        knowledgeOfTopic = findViewById(R.id.ratingBar_knowledge_of_topic)
        overall = findViewById(R.id.ratingBar_overall_rating)
        feedbackComplains = findViewById(R.id.feedback_complains)
        sendFeedbackButton = findViewById(R.id.send_feedback)
        mProgressBar = findViewById(R.id.progressBar_horizontal)
        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        sendFeedbackButton.setOnClickListener {
            if (clarity.rating == 0f || knowledgeOfTopic.rating == 0f || overall.rating == 0f) {
                showSnackBar("A rating of 0 is not allowed")
            } else {
                mProgressBar.visibility = View.VISIBLE

                disable()

                val clarityRating = clarity.rating
                val knowledgeRating = knowledgeOfTopic.rating
                val overallRating = overall.rating
                val comments = feedbackComplains.text.toString().trim()

                val feedback = SpeakerFeedback()
                feedback.clarity = clarityRating
                feedback.knowledgeOfTopic = knowledgeRating
                feedback.overall = overallRating
                feedback.comments = comments

                mRef.child("speakerFeedback")
                    .child(speakerName)
                    .child(mAuth.currentUser!!.uid)
                    .setValue(feedback)
                    .addOnCompleteListener(this, object : OnCompleteListener<Void?> {
                        override fun onComplete(task: Task<Void?>) {
                            if (task.isSuccessful) {
                                mProgressBar.visibility = View.GONE
                                showSnackBar("Thanks for your feedback")
                                enable()
                                clear()
                            } else {
                                enable()
                                mProgressBar.visibility = View.GONE
                                showSnackBar("Something went wrong")
                                Log.e(TAG, "Error", task.exception)
                            }
                        }
                    })
            }
        }
    }

    private fun disable() {
        clarity.isEnabled = false
        knowledgeOfTopic.isEnabled = false
        overall.isEnabled = false
        feedbackComplains.isEnabled = false
        sendFeedbackButton.text = "Sending..."
        sendFeedbackButton.isEnabled = false
    }

    private fun enable() {
        clarity.isEnabled = true
        knowledgeOfTopic.isEnabled = true
        overall.isEnabled = true
        feedbackComplains.isEnabled = true
        sendFeedbackButton.text = "Send Feedback"
    }

    private fun clear() {
        clarity.rating = 0f
        knowledgeOfTopic.rating = 0f
        overall.rating = 0f
        feedbackComplains.isEnabled = true
        feedbackComplains.text.clear()
        sendFeedbackButton.isEnabled = true
        sendFeedbackButton.text = "Send Feedback"
    }

    private fun showSnackBar(message: String) {
        val snackBar =
            Snackbar.make(sendFeedbackButton, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundResource(R.color.colorPrimary)
        snackBar.show()
    }

    companion object {
        const val TAG = "FeedbackActivity"
    }
}
