package com.example.infospot.UI

import am.appwise.components.ni.NoInternetDialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.infospot.Adapters.FragmentViewPagerAdapter
import com.example.infospot.DB.ArticleDatabase
import com.example.infospot.R
import com.example.infospot.Repository.NewsRepository
import com.example.infospot.fragments.BreakingNewsFragment
import com.example.infospot.fragments.SavedNewsFragment
import com.example.infospot.fragments.SearchNewsFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var noInternetDialog: NoInternetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        window.statusBarColor = Color.WHITE

        val NewsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(NewsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        //Creating Fragment Instances
        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        //Creating Viewpager Adapter
        val viewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager)

        //Adding Fragments To Viewpager
        viewPagerAdapter.addFragment(savedNewsFragment)
        viewPagerAdapter.addFragment(breakingNewsFragment)
        viewPagerAdapter.addFragment(searchNewsFragment)

        //Setting Adapter To Viewpager
        fragmentViewPager.adapter = viewPagerAdapter

        //Setting default Fragment
        fragmentViewPager.currentItem = 1

        //Setting Viewpager To TabBar
        bottomTabBar.setupBubbleTabBar(viewPager = fragmentViewPager)
        bottomTabBar.isFocusableInTouchMode = true
        bottomTabBar.touchscreenBlocksFocus = true

        noInternetDialog = noInternetAlert()

        bottomTabBar.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.breakingNewsFragment -> fragmentViewPager.currentItem = 1
                    R.id.savedNewsFragment -> fragmentViewPager.currentItem = 0
                    R.id.searchNewsFragment -> fragmentViewPager.currentItem = 2
                }
            }
        })

    }

    private fun noInternetAlert() = NoInternetDialog.Builder(this)
        .setCancelable(false)
        .setDialogRadius(50f)
        .setBgGradientCenter(resources.getColor(R.color.light_blue))
        .setBgGradientStart(resources.getColor(R.color.light_blue))
        .setBgGradientEnd(resources.getColor(R.color.light_blue))
        .setButtonColor(resources.getColor(R.color.white))
        .setButtonIconsColor(resources.getColor(R.color.light_blue))
        .setButtonTextColor(resources.getColor(R.color.black))
        .setWifiLoaderColor(resources.getColor(R.color.light_blue))
        .build()

}