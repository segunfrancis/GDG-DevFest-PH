package com.gdg.segunfrancis.gdgph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gdg.segunfrancis.gdgph.R
import com.gdg.segunfrancis.gdgph.utility.MethodUtils.Companion.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        val signOut = view.findViewById<TextView>(R.id.sign_out)
        signOut.setOnClickListener {
            if (DetailsActivity.auth.currentUser != null) {
                val signOutDialog = MaterialAlertDialogBuilder(view.context)
                    .setTitle("Are you sure you want to sign out?")
                    .setPositiveButton("YES") { dialogInterface, i ->
                        DetailsActivity.signOut()
                        dialogInterface.dismiss()
                        dialog?.dismiss()
                        loadImage(
                            view.context.applicationContext,
                            null,
                            R.drawable.avatar
                        )
                    }
                    .setNegativeButton("NO") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                signOutDialog.create().show()
            } else {
                // User already signed out
                Snackbar.make(view, "You are already signed out", Snackbar.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
        }
        return view
    }
}
