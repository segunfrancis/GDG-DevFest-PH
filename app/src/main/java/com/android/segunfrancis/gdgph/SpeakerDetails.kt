package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.segunfrancis.gdgph.model.Speakers
import com.android.segunfrancis.gdgph.ui.speakers.SpeakersViewModel
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.INTENT_KEY
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar

class SpeakerDetails : AppCompatActivity() {

    private lateinit var mSpeakerDetailsViewModel: SpeakerDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_details)

        val intent = intent
        val speakers: Speakers = intent.getSerializableExtra(INTENT_KEY) as Speakers

        val position = intent.getIntExtra("position", -1)
        val bundle = Bundle()
        bundle.putInt("position", position)

        mSpeakerDetailsViewModel =
            ViewModelProviders.of(this@SpeakerDetails, SpeakerDetailsViewModel(this.application, speakers)).get(SpeakerDetailsViewModel::class.java)
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
        val sessionRating: RatingBar = findViewById(R.id.speaker_rating_bar)
        Glide.with(this@SpeakerDetails)
            .load(speakers.profilePicture)
            .placeholder(R.drawable.avatar)
            .into(speakerImage)
        speakerTagLine.text = speakers.tagLine
        speakerBio.text = speakers.bio
        for (i in speakers.sessions) {
            speakerSession.text = i.toString()
        }
        mSpeakerDetailsViewModel.rating.observe(this, Observer {
            if (it != null) {
                sessionRating.rating = it.toFloat()
            }
        })
    }
}
