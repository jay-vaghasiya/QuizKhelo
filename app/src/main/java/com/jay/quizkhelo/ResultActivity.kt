package com.jay.quizkhelo

import LeaderboardAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.quizkhelo.databinding.ActivityResultBinding
import com.jay.quizkhelo.user.di.UserViewModel

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    private lateinit var adapter: LeaderboardAdapter
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        fetchLeaderBoardUSer()
    }

    private fun fetchLeaderBoardUSer() {
        viewModel.loadSortedUsers()
        viewModel.sortedUsers.observe(this) { sortedUsers ->
            adapter.updateUserList(sortedUsers)
        }
        binding.rvLeaderboard.layoutManager = LinearLayoutManager(this)
        adapter = LeaderboardAdapter()
        binding.rvLeaderboard.adapter = adapter
    }


}