package com.android.segunfrancis.gdgph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.viewpager2.widget.ViewPager2
import com.android.segunfrancis.gdgph.adapter.PagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val fragmentList = arrayListOf(OnBoardingFragment1.newInstance(), OnBoardingFragment2.newInstance())
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        viewPager.adapter = PagerAdapter(this@MainActivity, fragmentList)

        radioGroup.setOnCheckedChangeListener { group, i ->
            val checkedButton = group.findViewById<RadioButton>(i)
            val index = group.indexOfChild(checkedButton)
            viewPager.setCurrentItem(index, true)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // when current page change -> update radio button state
                val radioButtonId = radioGroup.getChildAt(position).id
                radioGroup.check(radioButtonId)
            }
        })
    }
}
