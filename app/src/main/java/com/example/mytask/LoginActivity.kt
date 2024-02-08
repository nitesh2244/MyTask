package com.example.mytask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.mytask.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initClick()
    }

    private fun initClick() {

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (isValidEmail(email) && email.isNotEmpty() && password.isNotEmpty()) {
                validateLoginCredentials(email, password)
            } else {
                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
                } else if (!isValidEmail(email)) {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun validateLoginCredentials(email: String, password: String) {
        val userRepository = UserRepository(this)
        val user = userRepository.getUserByEmail(email)
        val pass = userRepository.getUserByPassword(password)

        if (user != null) {

            if (user.email == email && pass?.password == password) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Toast.makeText(this, "User not found please Sign Up", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}