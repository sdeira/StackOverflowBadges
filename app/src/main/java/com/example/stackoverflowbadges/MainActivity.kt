package com.example.stackoverflowbadges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stackoverflowbadges.ui.badges.BadgesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, BadgesFragment.newInstance())
                    .commitNow()
        }
    }
}