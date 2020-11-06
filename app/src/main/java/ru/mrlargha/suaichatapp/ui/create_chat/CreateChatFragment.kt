package ru.mrlargha.suaichatapp.ui.create_chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.R
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentCreateChatBinding
import ru.mrlargha.suaichatapp.models.Chat
import ru.mrlargha.suaichatapp.models.CreateChatPayload
import ru.mrlargha.suaichatapp.models.User
import ru.mrlargha.suaichatapp.ui.users.UsersAdapter
import javax.inject.Inject

@AndroidEntryPoint
class CreateChatFragment : Fragment() {

    @Inject lateinit var chatsRepository: ChatsRepository
    private var selectedUsers = listOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCreateChatBinding.inflate(inflater, container, false)
        val token =
            activity?.getSharedPreferences("SP", Context.MODE_PRIVATE)?.getString("token", null)

        token?.let { t ->
            chatsRepository.apiService.getAllUsers(t).enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    binding.apply {
                        response.body()?.let {
                            usersRecycler.layoutManager = LinearLayoutManager(requireContext())

                            usersRecycler.adapter = UsersSelectableAdapter(it) { users ->
                                selectedUsers = users
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    showToast("Network error: ${t.localizedMessage}")
                }

            })

            binding.apply {
                create.setOnClickListener {
                    if (chatNameEdit.text.isNullOrBlank()) {
                        chatNameLayout.error = "Введите название чата!"
                        return@setOnClickListener
                    }

                    if (selectedUsers.size < 2) {
                        showToast("Выберите хотя бы 2х пользователей")
                        return@setOnClickListener
                    }

                    chatsRepository.apiService.createChat(
                        token,
                        CreateChatPayload(chatNameEdit.text.toString(), selectedUsers.map { it.id })
                    ).enqueue(object : Callback<Chat> {
                        override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                            response.body()?.let {
                                Navigation.findNavController(binding.root).navigate(CreateChatFragmentDirections.actionCreateChatFragmentToChatFragment(it.id))
                            }
                        }

                        override fun onFailure(call: Call<Chat>, t: Throwable) {
                            showToast("Network error: ${t.localizedMessage}")
                        }
                    })
                }
            }
        }

        return binding.root
    }

    private fun showToast(message: String) = Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    ).show()

}