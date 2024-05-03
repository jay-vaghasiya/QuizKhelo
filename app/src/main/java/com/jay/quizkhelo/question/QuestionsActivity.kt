package com.jay.quizkhelo.question

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.jay.quizkhelo.ResultActivity
import com.jay.quizkhelo.databinding.ActivityQuestionsBinding
import com.jay.quizkhelo.question.data.Result
import com.jay.quizkhelo.question.di.QuestionViewModel
import com.jay.quizkhelo.user.di.UserViewModel
import kotlinx.coroutines.launch

class QuestionsActivity : AppCompatActivity(), OptionsAdapter.OptionSelectionListener {
    private lateinit var binding: ActivityQuestionsBinding
    private lateinit var pagerAdapter: SurveyAdapter
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var questions: ArrayList<Result>
    private var currentUser: String = ""
    private val amount: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        currentUser = intent.getStringExtra("name").toString()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onResume() {
        super.onResume()
        getQuestion()
        clickListener()

    }

    private fun clickListener() {
        binding.btNext.setOnClickListener {

            if (binding.viewPager2.currentItem < pagerAdapter.itemCount - 1) {
                binding.viewPager2.setCurrentItem(binding.viewPager2.currentItem + 1, true)
            }

        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun viewpager(questions: ArrayList<Result>) {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.toolbar.title = "${position + 1}/10"
                if (position == questions.size - 1) {
                    binding.btNext.text = "Submit"

                } else {
                    binding.btNext.text = "Next"
                }
            }

        })
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getQuestion() {
        questionViewModel.questionList(amount)
        questionViewModel._userResponseLiveData.observe(this) { userResponse ->
            if (userResponse != null) {
                val size = userResponse.results.size
                Log.d("QuizKhelo", size.toString())

                questions = userResponse.results as ArrayList<Result>
                viewpager(questions)
                pagerAdapter = SurveyAdapter(this, userResponse.results, currentUser)
                binding.viewPager2.adapter = pagerAdapter
            }
        }
    }

    override fun onOptionSelected(option: String) {
        val currentQuestionIndex = binding.viewPager2.currentItem
        val currentQuestion = questions[currentQuestionIndex]

       val isCorrect = option == currentQuestion.correct_answer

       if (isCorrect) {
            val currentUser = intent.getStringExtra("name").toString()
            lifecycleScope.launch {
                val user = userViewModel.getUserByName(currentUser)
                user?.let {
                    val updatedUser = it.copy(trueAnswer = it.trueAnswer + 1)
                    userViewModel.updateUser(updatedUser)
                }
            }
        }


        if (currentQuestionIndex < questions.size - 1) {
            binding.viewPager2.setCurrentItem(currentQuestionIndex + 1, true)
        } else {
            if (binding.btNext.text == "Submit") {
                binding.btNext.setOnClickListener {
                    startActivity(Intent(this@QuestionsActivity, ResultActivity::class.java))
                    finish()
                }
            }
        }
    }


}