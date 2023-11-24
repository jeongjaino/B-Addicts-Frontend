import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.addicts.databinding.ItemIngChattingBinding
import com.umc.addicts.presentation.main.Profile

class ChattingRVAdapter(private val dataSet: List<Profile>) : RecyclerView.Adapter<ChattingRVAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemIngChattingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.apply {
                itemChattingTitleTv.text = profile.chatname
                itemChattingDateTv.text = profile.date
                realMemberTv.text = "12" // 실제 참가 인원
                recuritMemTv.text = "/25명" // 목표 참가 인원
                goalLimitTv.text = "4주" // 목표 기간
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}