package com.android.segunfrancis.gdgph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.android.segunfrancis.gdgph.adapter.PagerAdapter
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundImage = findViewById<ImageView>(R.id.background_image)
        Glide.with(this@MainActivity)
            .load(R.drawable.background_ph)
            .into(backgroundImage)

        setUpViewPager()
    }

    fun nextActivity(view: View) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun setUpViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val fragmentList = arrayListOf(OnBoardingFragment1.newInstance(), OnBoardingFragment2.newInstance())
        viewPager.adapter = PagerAdapter(this@MainActivity, fragmentList)
    }
}
