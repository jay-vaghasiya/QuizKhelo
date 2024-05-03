package com.jay.quizkhelo.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jay.quizkhelo.databinding.ActivityMainBinding
import com.jay.quizkhelo.question.QuestionsActivity
import com.jay.quizkhelo.user.data.User
import com.jay.quizkhelo.user.di.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setupStartButton()
    }

    private fun setupStartButton() {
        binding.btStart.setOnClickListener {
            val name = binding.etUsername.text.toString()
            if (name.isNotEmpty()) {
                val newUser = User(name = name, trueAnswer = 0)
                lifecycleScope.launch {
                    userViewModel.insertUser(newUser)
                    startQuiz(name)
                }
            }
        }
    }

    private fun startQuiz(name: String) {
        val intent = Intent(this, QuestionsActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

}