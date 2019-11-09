package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.android.segunfrancis.gdgph.model.Speakers
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.INTENT_KEY

class SpeakerDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_details)

        val intent = intent
        val toolbar = findViewById<Toolbar>(R.id.speaker_toolbar)
        setSupportActionBar(toolbar)
        val speakers: Speakers = intent.getSerializableExtra(INTENT_KEY) as Speakers
        supportActionBar?.title = speakers.fullName
    }
}
