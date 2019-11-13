package com.gdg.segunfrancis.gdgph.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gdg.segunfrancis.gdgph.ChatActivity
import com.gdg.segunfrancis.gdgph.DetailsActivity
import com.gdg.segunfrancis.gdgph.R
import com.gdg.segunfrancis.gdgph.adapter.ActivitiesAdapter
import com.gdg.segunfrancis.gdgph.utility.MethodUtils.Companion.showSnackBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mActivitiesAdapter: ActivitiesAdapter

    // Overriding default back button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialog = MaterialAlertDialogBuilder(context)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("YES") { dialogInterface, i ->
                        dialogInterface.dismiss()
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                    .setNegativeButton("NO") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                dialog.create().show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@HomeFragment, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        val fab = root.findViewById<ExtendedFloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            if (DetailsActivity.auth.currentUser == null) {
                showSnackBar("Sign in to use this feature")
            } else {
                startActivity(Intent(root.context, ChatActivity::class.java))
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)

        homeViewModel.recyclerView.observe(this, Observer {
            mActivitiesAdapter =
                ActivitiesAdapter(it, root.context)
            recyclerView.adapter = mActivitiesAdapter
            progressBar.visibility = View.GONE
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fab.isShown)
                    fab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        return root
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}