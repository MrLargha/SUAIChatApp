package ru.mrlargha.suaichatapp.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mrlargha.suaichatapp.R
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

            Picasso.get().load("http://fspobot.tw1.ru:8080/profile/getImage/${user.avatarName}")
                .error(
                    R.drawable.profile
                )
                .into(binding.userAvatar)

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