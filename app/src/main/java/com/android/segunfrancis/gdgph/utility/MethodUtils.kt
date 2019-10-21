package com.android.segunfrancis.gdgph.utility

import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import com.android.segunfrancis.gdgph.DetailsActivity
import com.android.segunfrancis.gdgph.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

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

        fun showSnackBar(message: String) {
            val snackBar =
                Snackbar.make(DetailsActivity.profileImage, message, Snackbar.LENGTH_LONG)
            val snackBarView = snackBar.view
            //snackBarView.setBackgroundColor(Resources.getSystem().getColor(R.color.colorPrimary))
            snackBarView.setBackgroundResource(R.color.colorPrimary)
            snackBar.show()
        }

        fun scaleAnimator(view: View) {
            val scaleAnimator =
                AnimatorInflater.loadAnimator(view.context, R.animator.scale_animation)
            scaleAnimator.apply {
                setTarget(view)
                start()
            }
        }
    }
}