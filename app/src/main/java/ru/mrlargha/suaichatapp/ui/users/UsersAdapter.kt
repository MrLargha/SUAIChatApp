package ru.mrlargha.suaichatapp.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.suaichatapp.databinding.UserViewBinding
import ru.mrlargha.suaichatapp.models.User

class UsersAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            val binding = UserViewBinding.bind(itemView)

            binding.apply {
                binding.username.text = user.firstName + " " + user.lastName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            UserViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}