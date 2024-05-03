import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jay.quizkhelo.databinding.ItemlistLeaderboardBinding
import com.jay.quizkhelo.user.data.User

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.OptionsViewHolder>() {

    private var userList = listOf<User>()

    inner class OptionsViewHolder(private val binding: ItemlistLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.tvNumber.text = (adapterPosition + 1).toString()
            binding.tvAnswer.text = "{${user.trueAnswer}/10}"
            binding.tvUsername.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemlistLeaderboardBinding.inflate(inflater, parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        if (userList.isNotEmpty()) {
            val user = userList[position]
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(updatedList: List<User>) {
        userList = updatedList
        notifyDataSetChanged()
    }
}
