package com.example.mytask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.mytask.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initClick()
    }

    private fun initClick() {

        binding.btnSignUp.setOnClickListener {
            val name = binding.edtName.text.toString()
            val number = binding.edtNumber.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (name.isEmpty() || number.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData(name, number, email, password)
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            }
        }

        binding.tvSignIN.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun saveUserData(
        name: String,
        number: String,
        email: String,
        password: String
    ) {
        val userRepository = UserRepository(this)
        val user = User(name = name, number = number, email = email, password = password)
        userRepository.addUser(user)
        Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}