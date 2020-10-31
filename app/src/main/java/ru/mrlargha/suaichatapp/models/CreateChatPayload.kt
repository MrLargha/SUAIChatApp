package ru.mrlargha.suaichatapp.models

data class CreateChatPayload(val chatName: String, val usersIds: List<Long>)