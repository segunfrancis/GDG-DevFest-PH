package com.gdg.segunfrancis.gdgph

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdg.segunfrancis.gdgph.model.SpeakerFeedback
import com.gdg.segunfrancis.gdgph.model.Speakers
import com.gdg.segunfrancis.gdgph.ui.speakers.SpeakersViewModel
import com.google.firebase.database.*

class SpeakerDetailsViewModel(var application: Application, var speaker: Speakers) : ViewModel(), ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpeakerDetailsViewModel(application, speaker) as T
    }

    private var mReference: DatabaseReference
    private var overallRating: Float? = null
    private var allRatings: ArrayList<Float?> = ArrayList()

    private val _rating = MutableLiveData<String>().apply {

        mReference = FirebaseDatabase.getInstance().reference
            .child("speakerFeedback")
            .child(speaker.fullName)
        Log.d("fullName", speaker.fullName)
        mReference.keepSynced(true)
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val speakerFeedback: SpeakerFeedback? =
                        snapshot.getValue(SpeakerFeedback::class.java)
                    overallRating = speakerFeedback?.overall
                    allRatings.add(overallRating)
                    Log.d("ratings: ", overallRating.toString())
                }
                val size = allRatings.size.toFloat()
                var sum = 0f
                for (ratings : Float? in allRatings) {
                        sum += ratings!!
                }
                val averageRating: Float? = sum.div(size)
                if (averageRating != null) {
                    value = averageRating.toString()
                    Log.d("averageRating: ", averageRating.toString())
                    Log.d("ratingSize: ", size.toString())
                    Log.d("ratingSum: ", sum.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    val rating: LiveData<String> = _rating
}