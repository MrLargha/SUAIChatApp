package ru.mrlargha.suaichatapp.models

import java.util.*

data class ChatMessage(val chat: Chat, val user: User, val content: String, val date: Date)