package ru.mrlargha.suaichatapp.ui.chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.R
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentChatBinding
import ru.mrlargha.suaichatapp.models.ChatMessage
import ru.mrlargha.suaichatapp.models.GetMessagesPayload
import ru.mrlargha.suaichatapp.models.SendMessagePayload
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    @Inject
    lateinit var chatsRepository: ChatsRepository
    private val navArgs: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        val token =
            activity?.getSharedPreferences("SP", Context.MODE_PRIVATE)?.getString("token", "-")

        binding.apply {
            messagesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

            sendMessage.setOnClickListener {
                if (!messageInput.text.isNullOrBlank()) {
                    chatsRepository.apiService.sendMessage(
                        token!!,
                        SendMessagePayload(navArgs.chatId, messageInput.text.toString().trim())
                    ).enqueue(
                        object : Callback<ChatMessage> {
                            override fun onResponse(
                                call: Call<ChatMessage>,
                                response: Response<ChatMessage>
                            ) {
                                response.body()?.let {
                                    (binding.messagesRecycler.adapter as ChatMessagesAdapter).apply {
                                        messages.add(0, it)
                                        notifyDataSetChanged()
                                    }
                                }

                            }

                            override fun onFailure(call: Call<ChatMessage>, t: Throwable) {
                                TODO("Not yet implemented")
                            }
                        }
                    )
                    messageInput.text?.clear()
                }
            }

        }


        chatsRepository.apiService.getMessages(token!!, navArgs.chatId).enqueue(
            object : Callback<List<ChatMessage>> {
                override fun onResponse(
                    call: Call<List<ChatMessage>>,
                    response: Response<List<ChatMessage>>
                ) {
                    response.body()?.let {
                        binding.messagesRecycler.adapter =
                            ChatMessagesAdapter(it.reversed().toMutableList(), 1)
                    }
                }

                override fun onFailure(call: Call<List<ChatMessage>>, t: Throwable) {
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )

        return binding.root
    }
}