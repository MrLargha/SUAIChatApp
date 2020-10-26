package ru.mrlargha.suaichatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

open class NetworkActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL = "http://fspobot.tw1.ru:8080/"
    }

    protected lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(this)
    }
}