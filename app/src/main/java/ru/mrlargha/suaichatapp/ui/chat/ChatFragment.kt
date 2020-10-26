package ru.mrlargha.suaichatapp.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import ru.mrlargha.suaichatapp.R
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentChatBinding
import javax.inject.Inject


class ChatFragment : Fragment() {

    @Inject
    lateinit var chatsRepository: ChatsRepository
    private val navArgs: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(inflater, container, false)



        return binding.root
    }
}