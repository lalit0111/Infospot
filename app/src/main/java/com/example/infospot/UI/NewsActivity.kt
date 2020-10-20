package com.example.infospot.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.infospot.Adapters.FragmentViewPagerAdapter
import com.example.infospot.fragments.BreakingNewsFragment
import com.example.infospot.R
import com.example.infospot.fragments.SavedNewsFragment
import com.example.infospot.fragments.SearchNewsFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        val viewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(savedNewsFragment)
        viewPagerAdapter.addFragment(breakingNewsFragment)
        viewPagerAdapter.addFragment(searchNewsFragment)
        fragmentViewPager.adapter=viewPagerAdapter

        bottomNavigationView.setupBubbleTabBar(viewPager = fragmentViewPager)

    }
}