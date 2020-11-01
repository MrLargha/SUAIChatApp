package ru.mrlargha.suaichatapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ru.mrlargha.suaichatapp.R
import ru.mrlargha.suaichatapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
        binding.textNotifications.setImageURI(uri)
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        getImage.launch("image/*")


//        startActivity(getPickImageIntent())

        return binding.root
    }

    private fun getPickImageIntent(): Intent {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra("return-data", true)

        return Intent.createChooser(pickIntent, "Выберите фотографию").apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayListOf(pickIntent, takePhotoIntent))
        }
    }
}