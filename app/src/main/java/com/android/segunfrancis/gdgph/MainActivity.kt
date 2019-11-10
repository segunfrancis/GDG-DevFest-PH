package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.android.segunfrancis.gdgph.adapter.PagerAdapter
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val fragmentList = arrayListOf(OnBoardingFragment1.newInstance(), OnBoardingFragment2.newInstance())
        viewPager.adapter = PagerAdapter(this@MainActivity, fragmentList)
    }
}
