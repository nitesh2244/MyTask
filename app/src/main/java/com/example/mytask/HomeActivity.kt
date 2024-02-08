package com.example.mytask

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytask.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val userRepository = UserRepository(this@HomeActivity)
        val users = userRepository.getAllUsers()
        initRecyclerView(users)
        initClick()

    }

    private fun initRecyclerView(list: List<User>) {
        binding.rvUserData.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvUserData.setHasFixedSize(true)
        binding.rvUserData.itemAnimator = DefaultItemAnimator()
        userAdapter = UserAdapter(this, list)
        binding.rvUserData.adapter = userAdapter
    }

    private fun initClick() {

        binding.floatBtn.setOnClickListener {
            showAddUserDialog()
        }

    }

    private fun showAddUserDialog() {
        val dialog = Dialog(this, R.style.Dialog_No_Border)
        dialog.setCanceledOnTouchOutside(true)
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        dialog.setContentView(R.layout.add_user_dialog)
        val btnYes = dialog.findViewById<TextView>(R.id.btnAdd)
        val name = dialog.findViewById<EditText>(R.id.edtNameD)
        val number = dialog.findViewById<EditText>(R.id.edtNumberD)
        val email = dialog.findViewById<EditText>(R.id.edtEmailD)
        val password = dialog.findViewById<EditText>(R.id.edtPasswordD)

        btnYes.setOnClickListener {

            val getName = name.text.toString()
            val getNumber = number.text.toString()
            val getEmail = email.text.toString()
            val getPassword = password.text.toString()

            if (getName.isEmpty() || getNumber.isEmpty() || getEmail.isEmpty() || getPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(getEmail)) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData(getName, getNumber, getEmail, getPassword)
                dialog.dismiss()
            }
        }

        dialog.show()
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
        Toast.makeText(this, "User add successfully", Toast.LENGTH_SHORT).show()
        this.recreate()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}