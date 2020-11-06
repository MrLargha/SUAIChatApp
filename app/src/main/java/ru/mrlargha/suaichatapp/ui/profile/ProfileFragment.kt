package ru.mrlargha.suaichatapp.ui.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mrlargha.suaichatapp.api.ChatsRepository
import ru.mrlargha.suaichatapp.databinding.FragmentProfileBinding
import ru.mrlargha.suaichatapp.models.User
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var chatsRepository: ChatsRepository
    private lateinit var binding: FragmentProfileBinding
    private var token: String? = null
    private var user: User? = null

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            binding.profileImage.setImageURI(uri)

            val bitmap = if(Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION") // No other simple way to do this in versions earlier than 28
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            }
             token?.let {
                 val stream = ByteArrayOutputStream()
                 bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                 val byteArray: ByteArray = stream.toByteArray()
                 chatsRepository.apiService.uploadProfileImage(
                     it,
                     MultipartBody.Part.createFormData(
                         "file",
                         "_user_${user?.id}" + uri.lastPathSegment,
                         RequestBody.create(MediaType.get("multipart/form-data"), byteArray)
                     )
                 ).enqueue(object : Callback<Void> {
                     override fun onResponse(call: Call<Void>, response: Response<Void>) {
                         Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT)
                             .show()
                     }

                     override fun onFailure(call: Call<Void>, t: Throwable) {
                         Toast.makeText(context, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT)
                             .show()
                     }

                 })
             }


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

        token =
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