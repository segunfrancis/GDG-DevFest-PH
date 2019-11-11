package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackActivity : AppCompatActivity() {

    lateinit var mProgressBar: ProgressBar
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


        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        sendFeedbackButton.setOnClickListener {
            mProgressBar.visibility = View.VISIBLE
            sendFeedbackButton.text = "Sending..."
            sendFeedbackButton.isEnabled = false
        }
    }

    private fun disable() {
        clarity.isEnabled = false
        knowledgeOfTopic.isEnabled = false
        overall.isEnabled = false
        feedbackComplains.isEnabled = false
    }

    private fun enable() {
        clarity.isEnabled = true
        knowledgeOfTopic.isEnabled = true
        overall.isEnabled = true
        feedbackComplains.isEnabled = true
    }

    private fun clear() {
        clarity.rating = 0f
        knowledgeOfTopic.rating = 0f
        overall.rating = 0f
        feedbackComplains.isEnabled = true
        feedbackComplains.text.clear()
        sendFeedbackButton.isEnabled = true
        sendFeedbackButton.text = "Send Feedback"
        recreate()
    }
}
