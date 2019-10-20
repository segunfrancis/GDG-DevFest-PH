package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton

class FeedbackActivity : AppCompatActivity() {

    lateinit var mMaterialButton: MaterialButton
    lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        mMaterialButton = findViewById(R.id.send_feedback)
        mProgressBar = findViewById(R.id.progressBar_horizontal)

        mMaterialButton.setOnClickListener {
            mProgressBar.visibility = View.VISIBLE
            mMaterialButton.text = "Sending..."
            mMaterialButton.isEnabled = false
        }
    }
}
