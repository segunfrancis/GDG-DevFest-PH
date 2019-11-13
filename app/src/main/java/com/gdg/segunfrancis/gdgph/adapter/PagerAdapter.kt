package com.gdg.segunfrancis.gdgph.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class PagerAdapter(fa: FragmentActivity, private var mFragments: ArrayList<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}