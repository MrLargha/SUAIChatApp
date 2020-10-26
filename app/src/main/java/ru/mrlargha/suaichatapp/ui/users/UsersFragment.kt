package ru.mrlargha.suaichatapp.ui.users

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.R
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentUsersBinding
import ru.mrlargha.suaichatapp.models.User
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {

    @Inject lateinit var chatsRepository: ChatsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUsersBinding.inflate(inflater, container, false)

        val token = activity?.getSharedPreferences("SP", MODE_PRIVATE)?.getString("token", null)

        token?.let {
            chatsRepository.apiService.getAllUsers(it).enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    binding.apply {
                        response.body()?.let {
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.adapter = UsersAdapter(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        return binding.root
    }
}