package ru.mrlargha.suaichatapp.ui.chats_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentChatsListBinding
import ru.mrlargha.suaichatapp.models.Chat
import ru.mrlargha.suaichatapp.models.User
import ru.mrlargha.suaichatapp.ui.users.UsersAdapter
import javax.inject.Inject

@AndroidEntryPoint
class ChatsListFragment : Fragment() {

    @Inject
    lateinit var chatsRepository: ChatsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsListBinding.inflate(inflater, container, false)

        val token = activity?.getSharedPreferences("SP", Context.MODE_PRIVATE)?.getString("token", null)

        token?.let {
            chatsRepository.apiService.getMyChats(it).enqueue(object : Callback<List<Chat>> {
                override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                    binding.apply {
                        response.body()?.let {
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.adapter = ChatsAdapter(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        return binding.root
    }
}