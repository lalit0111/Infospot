package com.example.infospot.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView

class FragmentViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()

    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}