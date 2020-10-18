package com.example.infospot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.infospot.R
import com.example.infospot.ui.Fragments.BreakingNewsFragment
import com.example.infospot.ui.Fragments.SavedNewsFragment
import com.example.infospot.ui.Fragments.SearchNewsFragment
import com.fxn.OnBubbleClickListener
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        setCurrentFragment(breakingNewsFragment)

        bottomNavigationView.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.breakingNewsFragment -> setCurrentFragment(breakingNewsFragment)
                    R.id.savedNewsFragment -> setCurrentFragment(savedNewsFragment)
                    R.id.searchNewsFragment -> setCurrentFragment(searchNewsFragment)
                }
            }
        })

    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}