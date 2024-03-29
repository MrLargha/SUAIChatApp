package ru.mrlargha.suaichatapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val token =
            getSharedPreferences("SP", Context.MODE_PRIVATE)?.getString("token", "-")
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@MainActivity, if(token == "-") LoginActivity::class.java else ChatHostActivity::class.java))
            finish()
        }

        supportActionBar?.hide()

    }
}