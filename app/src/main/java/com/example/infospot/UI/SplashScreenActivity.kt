package com.example.infospot.UI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.infospot.R
import com.example.infospot.utils.Constants.Companion.SPLASH_TIME_OUT

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.statusBarColor = resources.getColor(R.color.darkYellow, theme)

        Handler().postDelayed({
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}