package com.android.segunfrancis.gdgph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundImage = findViewById<ImageView>(R.id.background_image)
        Glide.with(this@MainActivity)
            .load(R.drawable.background_ph)
            .into(backgroundImage)
    }

    fun nextActivity(view: View) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}
