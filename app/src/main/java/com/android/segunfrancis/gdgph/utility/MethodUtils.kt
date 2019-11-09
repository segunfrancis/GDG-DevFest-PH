package com.android.segunfrancis.gdgph.utility

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import com.android.segunfrancis.gdgph.DetailsActivity
import com.android.segunfrancis.gdgph.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.makeramen.roundedimageview.RoundedImageView

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

        fun loadImage(context: Context, res: Int, image: RoundedImageView) {
            Glide.with(context)
                .load(res)
                .into(image)
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

        fun drawerNavigation(fragment: Fragment, tag: String) {
            fragment.fragmentManager?.beginTransaction()?.replace(R.id.container, fragment, tag)?.commit()
        }
    }
}