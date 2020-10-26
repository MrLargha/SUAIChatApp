package ru.mrlargha.suaichatapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.suaichatapp.databinding.ChatViewBinding
import ru.mrlargha.suaichatapp.models.Chat
import ru.mrlargha.suaichatapp.models.ChatMessage

class ChatMessagesAdapter(private val messages: List<ChatMessage>, private val userSelfId: Long) :
    RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
//            val binding = ChatViewBinding.bind(itemView)
//
//            binding.apply {
//
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageViewHolder(
            ChatViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size
}