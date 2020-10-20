package com.example.infospot.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.infospot.Adapters.FragmentViewPagerAdapter
import com.example.infospot.R
import com.example.infospot.fragments.BreakingNewsFragment
import com.example.infospot.fragments.SavedNewsFragment
import com.example.infospot.fragments.SearchNewsFragment
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //Creating Fragment Instances
        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        //Creating Veiwpager Adapter
        val viewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager)

        //Adding Fragments To Viewpager
        viewPagerAdapter.addFragment(savedNewsFragment)
        viewPagerAdapter.addFragment(breakingNewsFragment)
        viewPagerAdapter.addFragment(searchNewsFragment)

        //Setting Adapter To Viewpager
        fragmentViewPager.adapter = viewPagerAdapter

        //Setting Viewpager To TabBar
        bottomTabBar.setupBubbleTabBar(viewPager = fragmentViewPager)

    }
}