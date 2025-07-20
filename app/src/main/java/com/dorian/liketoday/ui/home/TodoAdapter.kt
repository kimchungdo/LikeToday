package com.dorian.liketoday.ui.home
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorian.liketoday.R
import com.dorian.liketoday.data.Todo
import java.time.LocalDateTime

class TodoAdapter(private val onToggle: (Todo) -> Unit) :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox = itemView.findViewById<CheckBox>(R.id.checkbox_todo)
        private val title = itemView.findViewById<TextView>(R.id.text_todo_title)
        private val time = itemView.findViewById<TextView>(R.id.create_time)

        fun bind(todo: Todo) {
            title.text = todo.title
            time.text = todo.date
            checkbox.isChecked = todo.isDone
            checkbox.setOnCheckedChangeListener(null)
            checkbox.setOnCheckedChangeListener { _, _ ->
                onToggle(todo)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
    }
}
