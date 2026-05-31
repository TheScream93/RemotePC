package com.example.remotepc

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            SettingsDialog().show(supportFragmentManager, "SettingsDialog")
        }
    }
}



