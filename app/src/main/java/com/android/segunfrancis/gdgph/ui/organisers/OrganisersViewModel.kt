package com.android.segunfrancis.gdgph.ui.organisers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.segunfrancis.gdgph.adapter.OrganisersAdapter
import com.android.segunfrancis.gdgph.model.Activities
import com.android.segunfrancis.gdgph.model.Organisers
import com.google.firebase.database.*

class OrganisersViewModel : ViewModel() {

    private var mReference: DatabaseReference
    private var mOrganisersList: List<Organisers> = ArrayList()

    private val _recyclerView = MutableLiveData<List<Organisers>>().apply {
        mReference = FirebaseDatabase.getInstance().reference.child("organisers")
        mReference.keepSynced(true)
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (mOrganisersList as ArrayList<Organisers>).clear()
                for (snapshot in dataSnapshot.children) {
                    val organisers: Organisers? = snapshot.getValue(Organisers::class.java)
                    if (organisers != null) {
                        (mOrganisersList as ArrayList<Organisers>).add(organisers)
                    }
                }
                value = mOrganisersList
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {

            }
        })
    }
    val recyclerView: MutableLiveData<List<Organisers>> = _recyclerView
}