package com.dorian.liketoday.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorian.liketoday.databinding.FragmentHomeBinding
import com.dorian.liketoday.viewmodel.TodoViewModel
import androidx.fragment.app.viewModels
import com.dorian.liketoday.R
import com.dorian.liketoday.data.Todo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter
    private lateinit var today: String
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        adapter = TodoAdapter {todo -> viewModel.update(todo.copy(isDone = !todo.isDone))}

        val recyclerView = binding!!.recyclerTodo
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getTodosByDate(today).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        val addButton = binding!!.fabAddTodo
        val inputField = binding!!.editTodoInput
        val weightButton = binding!!.buttonToWeightFragment

        addButton.setOnClickListener {
            val text = inputField.text.toString()
            if (text.isNotBlank()) {
                viewModel.insert(Todo(title = text, date = today))
                inputField.text.clear()
            }
        }

        weightButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frag_container_nav, WeightFragment())
                .addToBackStack(null)
                .commit()
        }


        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}