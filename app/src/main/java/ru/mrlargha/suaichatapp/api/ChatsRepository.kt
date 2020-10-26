package ru.mrlargha.suaichatapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatsRepository @Inject constructor(){
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://fspobot.tw1.ru:8080/")
        .build()
    val apiService = retrofit.create(ChatsAPIService::class.java)
}