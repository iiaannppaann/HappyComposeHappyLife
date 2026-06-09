package com.company.demo.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.demo.R
import com.company.demo.core.ui.MainMenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainMenuFragment())
                .commit()
        }
    }
}
