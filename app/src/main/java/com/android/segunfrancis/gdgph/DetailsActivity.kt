package com.android.segunfrancis.gdgph

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.loadImage
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.showSnackBar
import com.android.segunfrancis.gdgph.utility.MethodUtils.Companion.updateSignInText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.makeramen.roundedimageview.RoundedImageView

import java.lang.Exception

class DetailsActivity : AppCompatActivity() {

    private lateinit var photoUri: Uri
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var progressBar: ProgressBar
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        DetailsActivity.Companion.setContext(this)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        // Initiate navigation header view items
        val headerView = navView.getHeaderView(0)
        displayName = headerView.findViewById(R.id.display_name)
        emailAddress = headerView.findViewById(R.id.email_address)
        profileImage = headerView.findViewById(R.id.profile_image)
        loginButton = headerView.findViewById(R.id.logout_button)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about_us, R.id.nav_feedback,
                R.id.nav_join_gdg, R.id.nav_speakers, R.id.nav_organisers
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

       /* navView.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            var fragment: Fragment? = null
            var tag = ""
            when (id) {
                R.id.nav_home -> {
                    fragment = HomeFragment()
                    tag = "HomeFragment"
                }
                R.id.nav_feedback -> {
                    fragment = FeedbackFragment()
                    tag = "FeedbackFragment"
                }
                R.id.nav_about_us -> {
                    fragment = AboutFragment()
                    tag = "AboutFragment"
                }
                R.id.nav_join_gdg -> {
                    fragment = JoinGDGFragment()
                    tag = "JoinGDGFragment"
                }
                R.id.nav_organisers -> {
                    fragment = OrganisersFragment()
                    tag = "OrganisersFragment"
                }
                R.id.nav_share -> {
                    fragment = SpeakersFragment()
                    tag = "SpeakersFragment"
                }
            }
            if (fragment != null) {
                drawerNavigation(fragment, tag)
            }
            true
        }*/

        progressBar = findViewById(R.id.progress_bar_detail)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            if (auth.currentUser == null) {
                drawerLayout.closeDrawer(GravityCompat.START)
                progressBar.visibility = View.VISIBLE
                // Google sign in
                signIn()
            } else {
                val signOutDialog = MaterialAlertDialogBuilder(this@DetailsActivity)
                signOutDialog.apply {
                    setMessage("Are you sure you want to sign out?")
                    setPositiveButton("YES") { dialogInterface, i ->
                        signOut()
                        updateSignInText("", "", "Sign in")
                        dialogInterface.dismiss()
                    }
                    setNegativeButton("NO") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                }
                signOutDialog.create().show()
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, "bottom_sheet")
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in and update UI accordingly
        if (auth.currentUser != null) {
            val currentUser = auth.currentUser
            photoUri = currentUser?.photoUrl!!
            updateSignInText(
                auth.currentUser?.displayName.toString(),
                auth.currentUser?.email.toString(),
                "Sign out"
            )
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
                showSnackBar("Google sign in successful")
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
                updateSignInText(
                    auth.currentUser?.displayName.toString(),
                    auth.currentUser?.email.toString(),
                    "Sign out"
                )
            } catch (e: Exception) {
                // Google sign in failed
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Error ${e.message}", Toast.LENGTH_LONG).show()
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                    updateSignInText("", "", "Sign in")
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
                    showSnackBar("Authentication Successful.")
                    photoUri = user?.photoUrl!!
                    loadImage(this, photoUri, 0)
                    Log.d(TAG, "Photo URI:  $photoUri")
                    progressBar.visibility = View.GONE
                    updateSignInText(
                        auth.currentUser?.displayName.toString(),
                        auth.currentUser?.email.toString(),
                        "Sign out"
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showSnackBar("Authentication Failed.")
                    loginButton.text = "Sign In"
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
        lateinit var profileImage: RoundedImageView
        lateinit var displayName: TextView
        lateinit var emailAddress: TextView
        lateinit var loginButton: Button
        private lateinit var context: Context

        fun setContext(context: Context) {
            this.context = context
        }

        fun signOut() {
            // Firebase sign out
            auth.signOut()

            // Google sign out
            googleSignInClient.signOut().addOnSuccessListener {
                showSnackBar("Signed Out")
                updateSignInText("", "", "Sign In")
            }
            loadImage(context, null, R.drawable.avatar)
        }

        fun alphaAnimation(view: View) {
            val alphaAnimation =
                AnimatorInflater.loadAnimator(view.context, R.animator.alpha_animation)
            alphaAnimation.apply {
                setTarget(view)
                start()
            }
        }
    }
}
