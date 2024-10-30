package com.example.examensegundoparcial

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET, android.Manifest.permission.POST_NOTIFICATIONS), 0)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.over_flow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.catalog -> {
                startActivity(Intent(this, CatalogActivity::class.java))
                true
            }
            R.id.video ->{
                startActivity(Intent(this, VideoActivity::class.java))
                true
            }
            R.id.sensor ->{
                startActivity(Intent(this, SensorActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}