package com.gdg.segunfrancis.gdgph.ui.speakers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdg.segunfrancis.gdgph.model.Speakers
import com.google.firebase.database.*

class SpeakersViewModel : ViewModel() {

    private var mReference: DatabaseReference
    private var mSpeakersList: List<Speakers> = ArrayList()

    private val recyclerView = MutableLiveData<List<Speakers>>().apply {
        mReference = FirebaseDatabase.getInstance().reference.child("speakers")
        mReference.keepSynced(true)
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (mSpeakersList as ArrayList<Speakers>).clear()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val speakers: Speakers? = snapshot.getValue(Speakers::class.java)
                    if (speakers != null) {
                        (mSpeakersList as ArrayList<Speakers>).add(speakers)
                    }
                }
                value = mSpeakersList
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {

            }
        })
    }
    val text: LiveData<List<Speakers>> = recyclerView
}