package ru.mrlargha.suaichatapp.api

import retrofit2.Call
import retrofit2.http.*
import ru.mrlargha.suaichatapp.models.*

interface ChatsAPIService {
    @GET("/chats/allusers")
    fun getAllUsers(@Header("Authorization") token: String): Call<List<User>>

    @POST("/chats/createChat")
    fun createChat(@Header("Authorization") token: String, @Body body: CreateChatPayload) : Call<Chat>

    @GET("/chats/myChats")
    fun getMyChats(@Header("Authorization") token: String): Call<List<Chat>>

    @GET("/chats/getMessages")
    fun getMessages(
        @Header("Authorization") token: String,
        @Query("chatId") chatId: Long
    ): Call<List<ChatMessage>>

    @GET("/profile/getInfoAboutMe")
    fun getInfoAboutMe(@Header("Authorization") token: String) : Call<User>

    @POST("/chats/sendMessage")
    fun sendMessage(@Header("Authorization") token: String, @Body body: SendMessagePayload) : Call<ChatMessage>

}