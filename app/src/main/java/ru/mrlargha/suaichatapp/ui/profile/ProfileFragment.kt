package ru.mrlargha.suaichatapp.ui.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentProfileBinding
import ru.mrlargha.suaichatapp.models.User
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var chatsRepository: ChatsRepository
    private lateinit var binding: FragmentProfileBinding
    private var user: User? = null

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            binding.profileImage.setImageURI(uri)
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            getImageLauncher.launch("image/*")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val token =
            activity?.getSharedPreferences("SP", Context.MODE_PRIVATE)?.getString("token", null)

        token?.let {
            chatsRepository.apiService.getInfoAboutMe(it).enqueue(
                object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        response.body()?.let {
                            user = it
                            displayInfo()
                        }

                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(context, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            )
        }

        binding.button.setOnClickListener {
            if (requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                getImageLauncher.launch("image/*")
            }
        }



        return binding.root
    }

    private fun displayInfo() {
        user?.let {
            binding.userName.text = "${it.firstName} ${it.lastName}"
            Picasso.get().load("http://fspobot.tw1.ru:8080/profile/getImage/${it.avatarName}")
                .into(binding.profileImage)
        }
    }

}