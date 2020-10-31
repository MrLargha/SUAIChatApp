package ru.mrlargha.suaichatapp.ui.create_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.suaichatapp.databinding.SelectableUserViewBinding
import ru.mrlargha.suaichatapp.databinding.UserViewBinding
import ru.mrlargha.suaichatapp.models.User

class UsersSelectableAdapter(
    private val users: List<User>,
    private val selectionChangedCallback: (users: List<User>) -> Unit
) :
    RecyclerView.Adapter<UsersSelectableAdapter.UserViewHolder>() {

    private val selectedUsers = mutableListOf<User>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding = SelectableUserViewBinding.bind(itemView)

        init {
            binding.addCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    selectedUsers.add(users[adapterPosition])
                } else {
                    selectedUsers.remove(users[adapterPosition])
                }
                selectionChangedCallback.invoke(selectedUsers)
            }
        }

        fun bind(user: User) {
            binding.apply {
                binding.username.text = user.firstName + " " + user.lastName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            SelectableUserViewBinding.inflate(
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