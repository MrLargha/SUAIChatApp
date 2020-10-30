package ru.mrlargha.suaichatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import ru.mrlargha.suaichatapp.databinding.ActivityLoginBinding

class LoginActivity : NetworkActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
            login.setOnClickListener {
                val email = loginTextEdit.text.toString()
                val pass = passwordTextEdit.text.toString()

                if (email.isBlank() || pass.isBlank()) {
                    Toast.makeText(this@LoginActivity, "Заполните все поля", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val url = BASE_URL + "auth/login"
                val request = JsonObjectRequest(
                    POST,
                    url,
                    JSONObject("{ \"email\": \"$email\", \"password\": \"$pass\" }"),
                    {
                        Toast.makeText(this@LoginActivity, "OK", Toast.LENGTH_SHORT).show()

                        getSharedPreferences("SP", MODE_PRIVATE).edit().apply {
                            putString("token", it.getString("token"))
                            apply()
                        }

                        startActivity(Intent(this@LoginActivity, ChatHostActivity::class.java))

                        finish()
                    },
                    {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error! $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                requestQueue.add(request)
            }

            register.setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        RegistrationActivity::class.java
                    )
                )
            }
        }

    }
}