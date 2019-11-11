package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.android.segunfrancis.gdgph.model.Speakers
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.INTENT_KEY
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar

class SpeakerDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_details)

        val intent = intent
        val speakers: Speakers = intent.getSerializableExtra(INTENT_KEY) as Speakers
        val toolbar = findViewById<MaterialToolbar>(R.id.speaker_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = speakers.fullName
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val speakerImage: ImageView = findViewById(R.id.speaker_image_view)
        val speakerTagLine: TextView = findViewById(R.id.speaker_tagLine_textView)
        val speakerBio: TextView = findViewById(R.id.speaker_bio_textView)
        val speakerSession: TextView = findViewById(R.id.speaker_session_textView)
        Glide.with(this@SpeakerDetails)
            .load(speakers.profilePicture)
            .placeholder(R.drawable.avatar)
            .into(speakerImage)
        speakerTagLine.text = speakers.tagLine
        speakerBio.text = speakers.bio
        for (i in speakers.sessions) {
            speakerSession.text = i.toString()
        }
    }
}
