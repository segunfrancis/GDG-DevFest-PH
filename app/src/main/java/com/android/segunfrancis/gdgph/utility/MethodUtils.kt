package com.android.segunfrancis.gdgph.utility

import android.content.Context
import android.net.Uri
import com.android.segunfrancis.gdgph.DetailsActivity
import com.android.segunfrancis.gdgph.R
import com.bumptech.glide.Glide

class MethodUtils {
    companion object {
        fun loadImage(context: Context, uri: Uri?, id: Int) {
            when {
                id == 0 -> Glide.with(context)
                    .load(uri)
                    .into(DetailsActivity.profileImage)
                uri == null -> Glide.with(context)
                    .load(id)
                    .into(DetailsActivity.profileImage)
                else -> Glide.with(context)
                    .load(R.drawable.avatar)
                    .into(DetailsActivity.profileImage)
            }
        }

        fun updateSignInText(name: String, email: String, status: String) {
            DetailsActivity.displayName.text = name
            DetailsActivity.emailAddress.text = email
            DetailsActivity.loginButton.text = status
        }
    }
}