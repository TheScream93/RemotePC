package com.example.remotepc

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val deviceListButton = findViewById<ImageButton>(R.id.devicesButton)

        settingsButton.setOnClickListener {
            SettingsDialog().show(supportFragmentManager, "SettingsDialog")
        }

        deviceListButton.setOnClickListener {
            DeviceListDialog().show(supportFragmentManager, "DeviceListDialog")
        }
    }
}



