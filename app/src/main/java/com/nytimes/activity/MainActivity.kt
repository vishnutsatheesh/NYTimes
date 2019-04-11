package com.nytimes.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nytimes.R
import com.nytimes.fragment.ArticleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ArticleFragment.newInstance())
                    .commitNow()
        }
    }

}
