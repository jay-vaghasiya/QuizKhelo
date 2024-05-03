package com.jay.quizkhelo.question

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jay.quizkhelo.question.data.Result

class SurveyAdapter(
    private val fragmentActivity: FragmentActivity,
    private val questionModels: ArrayList<Result>, private val currentUser: String
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return questionModels.size
    }

    override fun createFragment(position: Int): Fragment {
        val questionModel = questionModels[position]
        return QuestionFragment.newInstance(questionModel, currentUser)
    }


}