package ru.mrlargha.suaichatapp.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.mrlargha.suaichatapp.models.*

interface ChatsAPIService {
    @GET("/chats/allusers")
    fun getAllUsers(@Header("Authorization") token: String): Call<List<User>>

    @POST("/chats/createChat")
    fun createChat(@Header("Authorization") token: String, @Body body: CreateChatPayload) : Call<Chat>

    @GET("/chats/myChats")
    fun getMyChats(@Header("Authorization") token: String): Call<List<Chat>>

    @GET("/chats/messages")
    fun getMessages(
        @Header("Authorization") token: String,
        @Body body: GetMessagesPayload
    ): Call<List<ChatMessage>>

    @POST("/chats/sendMessage")
    fun sendMessage(@Header("Authorization") token: String, @Body body: SendMessagePayload) : Call<ChatMessage>

}