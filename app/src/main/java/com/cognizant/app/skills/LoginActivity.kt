package com.cognizant.app.skills

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cognizant.app.skills.SearchListActivity
import com.cognizant.app.skills.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btLogin.setOnClickListener {
                if (TextUtils.isEmpty(etUsername.text.toString())) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please input your Cognizant ID",
                        Toast.LENGTH_LONG
                    ).show()
                    if (TextUtils.isEmpty(etPassword.text.toString())) {
                        Toast.makeText(this@LoginActivity, "Invalid Password", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    val intent = Intent(this@LoginActivity, SearchListActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}