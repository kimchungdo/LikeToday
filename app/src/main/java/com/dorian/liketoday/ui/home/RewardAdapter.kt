package com.dorian.liketoday.ui.home
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorian.liketoday.R
import com.dorian.liketoday.data.Reward

class RewardAdapter(private val onClick: (Reward) -> Unit) :
    ListAdapter<Reward, RewardAdapter.RewardViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = getItem(position)
        holder.bind(reward)
    }

    inner class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.text_reward_title)
        private val desc = itemView.findViewById<TextView>(R.id.text_reward_desc)

        fun bind(reward: Reward) {
            title.text = reward.title
            desc.text = if (reward.isUnlocked) "[사용 가능] ${reward.description}" else "🔒 ${reward.description}"
            itemView.setOnClickListener { onClick(reward) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Reward>() {
        override fun areItemsTheSame(oldItem: Reward, newItem: Reward) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Reward, newItem: Reward) = oldItem == newItem
    }
}
