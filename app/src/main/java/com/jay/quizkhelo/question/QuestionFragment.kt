package com.jay.quizkhelo.question

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.quizkhelo.R
import com.jay.quizkhelo.databinding.FragmentQuestionBinding
import com.jay.quizkhelo.question.data.Result
import com.jay.quizkhelo.user.data.User
import com.jay.quizkhelo.user.di.UserViewModel
import com.jay.quizkhelo.util.CountdownCircularProgressBar
import kotlinx.coroutines.launch


class QuestionFragment : Fragment(), OptionsAdapter.OptionSelectionListener {

    internal lateinit var questionModel: Result
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var optionsAdapter: OptionsAdapter
    private lateinit var currentUser: String
    private lateinit var userViewModel: UserViewModel
    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private lateinit var sharedPreferences: SharedPreferences


    companion object {
        private const val ARG_CURRENT_USER = "current_user"
        fun newInstance(
            questionModel: Result,
            currentUser: String
        ): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle().apply {
                putString(ARG_CURRENT_USER, currentUser)
            }
            fragment.questionModel = questionModel
            fragment.arguments = args
            return fragment
        }}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedPosition", selectedPosition)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savedInstanceState?.let {
            selectedPosition = it.getInt("selectedPosition", RecyclerView.NO_POSITION)
        }
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharedPreferences = requireActivity().getSharedPreferences(
            "QuizKheloPrefs",
            Context.MODE_PRIVATE
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            currentUser = args.getString(ARG_CURRENT_USER)!!
        }
        selectedPosition = sharedPreferences.getInt("selectedPosition", RecyclerView.NO_POSITION)
        setupProgressbar()
        setupOptions()
    }

    private fun setupProgressbar() {
        val circularProgressBar =
            view?.findViewById<CountdownCircularProgressBar>(R.id.circularProgressBar)
        circularProgressBar?.setMaxTimeMillis(30000)
        circularProgressBar?.setProgressColor(color = R.color.green300)
        circularProgressBar?.setTextColor(color = R.color.green300)
    }

    private fun setupOptions() {
        binding.tvQuestion.text = questionModel.question
        binding.rvOptions.layoutManager = LinearLayoutManager(requireContext())
        val incorrectAnswers = questionModel.incorrect_answers
        val correctAnswer = questionModel.correct_answer
        val allOptions = incorrectAnswers.toMutableList().apply { add(correctAnswer) }
        allOptions.shuffle()
        optionsAdapter = OptionsAdapter(allOptions,this)
        binding.rvOptions.adapter = optionsAdapter


    }

    override fun onOptionSelected(option: String) {
        if (activity is OptionsAdapter.OptionSelectionListener) {
            (activity as OptionsAdapter.OptionSelectionListener).onOptionSelected(option)
            // Save selected position to SharedPreferences
            sharedPreferences.edit().putInt("selectedPosition", selectedPosition).apply()
        }
    }

}
