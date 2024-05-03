package com.jay.quizkhelo.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jay.quizkhelo.databinding.ItemlistOptionsBinding

class OptionsAdapter(
    private val optionList: List<String>,
    private val listener: OptionSelectionListener
) : RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class OptionsViewHolder(private val binding: ItemlistOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvOption.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    setSelectedPosition(position)
                    listener.onOptionSelected(optionList[position])
                }
            }
        }

        fun bind(option: String, isSelected: Boolean) {
            binding.tvOption.text = option
            binding.tvOption.isSelected = isSelected
            binding.rbOption.isChecked = isSelected  // Check radio button if selected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemlistOptionsBinding.inflate(inflater, parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        val option = optionList[position]
        holder.bind(option, position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    interface OptionSelectionListener {
        fun onOptionSelected(option: String)
    }
}
