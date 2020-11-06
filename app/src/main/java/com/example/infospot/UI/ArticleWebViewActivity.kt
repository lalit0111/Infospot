package com.example.infospot.UI

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.infospot.R
import kotlinx.android.synthetic.main.activity_article_web_view.*

class ArticleWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_view)

        val articleUrl = intent.getStringExtra("articleURL")

        articleWebView.apply {
            webViewClient = WebViewClient()
            articleUrl?.let {
                loadUrl(it)
            }
        }
    }
}