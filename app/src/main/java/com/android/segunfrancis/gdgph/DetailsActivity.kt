package com.android.segunfrancis.gdgph

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.makeramen.roundedimageview.RoundedImageView

import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {

    private lateinit var photoUri: Uri
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val welcomeText = findViewById<TextView>(R.id.welcome_text)
        alphaAnimation(welcomeText)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        profileImage = findViewById(R.id.profile_image)
        FAB = findViewById(R.id.fab)
        progressBar = findViewById(R.id.progress_bar)

        profileImage.setOnClickListener { view ->
            if (auth.currentUser == null) {
                progressBar.visibility = View.VISIBLE
                // Google sign in
                signIn()
            } else {
                Snackbar.make(FAB, "Already Signed In", Snackbar.LENGTH_SHORT).show()
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, "bottom_sheet")
        }

        FAB.setOnClickListener {
            if (auth.currentUser == null) {
                Snackbar.make(FAB, "Sign in to use this feature", Snackbar.LENGTH_LONG).show()
                scaleAnimator(profileImage)
            } else {
                startActivity(Intent(this@DetailsActivity, ChatActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in and update UI accordingly
        if (auth.currentUser != null) {
            val currentUser = auth.currentUser
            photoUri = currentUser?.photoUrl!!
        }
        setUpAuth()
        attachListener()
    }

    override fun onPause() {
        super.onPause()
        detachListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the intent from GoogleSignInApi.getSignInIntent
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google sign in was successful, authenticate with firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
                Snackbar.make(fab, "Google sign in successful", Snackbar.LENGTH_SHORT).show()
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                // Google sign in failed
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "FirebaseAuthWithGoogle: " + account.id!!)
        progressBar.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Snackbar.make(fab, "Authentication Successful.", Snackbar.LENGTH_SHORT)
                        .show()
                    photoUri = user?.photoUrl!!
                    loadImage(this, photoUri, 0)
                    Log.d(TAG, "Photo URI:  $photoUri")
                    progressBar.visibility = View.GONE
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(fab, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                progressBar.visibility = View.GONE
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun setUpAuth() {
        authStateListener = FirebaseAuth.AuthStateListener {
            val user = auth.currentUser
            if (user != null) {
                // User is already signed in
                photoUri = user.photoUrl!!
                loadImage(this, photoUri, 0)
                /*Glide.with(this@DetailsActivity)
                    .load(photoUri)
                    .placeholder(R.drawable.ic_person)
                    //.apply(RequestOptions.circleCropTransform())
                    .into(profile_image)*/
            }
        }
    }

    private fun attachListener() {
        auth.addAuthStateListener(authStateListener)
    }

    private fun detachListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    companion object {
        private const val RC_SIGN_IN = 23
        private const val TAG = "DetailsActivity"
        private lateinit var googleSignInClient: GoogleSignInClient
        lateinit var auth: FirebaseAuth
        private lateinit var FAB: ExtendedFloatingActionButton
        private lateinit var profileImage: RoundedImageView
        private lateinit var progressBar: ProgressBar

        fun signOut() {
            // Firebase sign out
            auth.signOut()

            // Google sign out
            googleSignInClient.signOut().addOnSuccessListener {
                Snackbar.make(FAB, "Signed Out", Snackbar.LENGTH_SHORT).show()
            }
        }

        fun loadImage(context: Context, uri: Uri?, id: Int) {
            when {
                id == 0 -> Glide.with(context)
                    .load(uri)
                    .into(profileImage)
                uri == null -> Glide.with(context)
                    .load(id)
                    .into(profileImage)
                else -> Glide.with(context)
                    .load(R.drawable.ic_person)
                    .into(profileImage)
            }
        }

        fun scaleAnimator(view: View) {
            val scaleAnimator = AnimatorInflater.loadAnimator(view.context, R.animator.scale_animation)
            scaleAnimator.apply {
                setTarget(view)
                start()
            }
        }

        fun alphaAnimation(view: View) {
            val alphaAnimation = AnimatorInflater.loadAnimator(view.context, R.animator.alpha_animation)
            alphaAnimation.apply {
                setTarget(view)
                start()
            }
        }
    }
}
