package com.android.segunfrancis.gdgph.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.segunfrancis.gdgph.adapter.ActivitiesAdapter
import com.android.segunfrancis.gdgph.model.Activities
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeViewModel : ViewModel() {

    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var mReference: DatabaseReference
    private var mList: List<Activities> = ArrayList()
    private val TAG = "ViewModel"

    private val _recyclerView = MutableLiveData<List<Activities>>().apply {
        mReference = FirebaseDatabase.getInstance().reference.child("schedule")
        mReference.keepSynced(true)
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //(mList as ArrayList<Activities>).clear()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val activity: Activities? = snapshot.getValue(Activities::class.java)
                    if (activity != null) {
                        (mList as ArrayList<Activities>).add(activity)
                        Log.d(TAG, "ViewModel list: $mList")
                    }
                }
                value = mList
                Log.d(TAG, "ViewModel value: $mList")
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    val recyclerView: MutableLiveData<List<Activities>> = _recyclerView
}