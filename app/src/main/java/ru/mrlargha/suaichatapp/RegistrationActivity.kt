package ru.mrlargha.suaichatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import ru.mrlargha.suaichatapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : NetworkActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            login.setOnClickListener {
                val email = loginTextEdit.text.toString()
                val pass = passwordTextEdit.text.toString()
                val lastName = lastNameTextEdit.text.toString()
                val firstName = firstNameTextEdit.text.toString()

                if (email.isBlank() || pass.isBlank() || firstName.isBlank() || lastName.isBlank()) {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Заполните все поля",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }

                val url = BASE_URL + "auth/register"
                val request = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    JSONObject("{ \"email\": \"$email\", \"password\": \"$pass\", \"firstName\": \"$firstName\", \"lastName\": \"$lastName\" }"),
                    {
                        Toast.makeText(this@RegistrationActivity, "OK", Toast.LENGTH_SHORT).show()

                        getSharedPreferences("SP", MODE_PRIVATE).edit().apply {
                            putString("token", it.getString("token"))
                            apply()
                        }

                        startActivity(Intent(this@RegistrationActivity, ChatHostActivity::class.java))

                        finish()

                    },
                    {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Error! ${it.networkResponse.statusCode}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                requestQueue.add(request)
            }
            register.setOnClickListener { finish() }
        }
    }
}