package ru.mrlargha.suaichatapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.suaichatapp.databinding.MessageViewBinding
import ru.mrlargha.suaichatapp.models.ChatMessage

class ChatMessagesAdapter(val messages: MutableList<ChatMessage>, private val userSelfId: Long) :
    RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            val binding = MessageViewBinding.bind(itemView)

            binding.apply {
                messageContent.text = message.content
                author.text = message.user.firstName + " " + message.user.lastName
                date.text = message.date.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageViewHolder(
            MessageViewBinding.inflate(
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