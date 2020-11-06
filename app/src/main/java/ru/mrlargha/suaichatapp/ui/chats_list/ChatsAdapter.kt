package ru.mrlargha.suaichatapp.ui.chats_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.suaichatapp.databinding.ChatViewBinding
import ru.mrlargha.suaichatapp.models.Chat

class ChatsAdapter(private val chats: List<Chat>) :
    RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(ChatsListFragmentDirections.actionNavigationChatsToChatFragment(chats[adapterPosition].id))

            }
        }

        fun bind(chat: Chat) {
            val binding = ChatViewBinding.bind(itemView)

            binding.apply {
                binding.chatName.text = chat.chatName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChatViewHolder(
            ChatViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount() = chats.size
}