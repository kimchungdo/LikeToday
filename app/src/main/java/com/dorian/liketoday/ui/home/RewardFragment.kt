package com.dorian.liketoday.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dorian.liketoday.R
import com.dorian.liketoday.viewmodel.RewardViewModel

class RewardFragment : Fragment() {
    private val rewardViewModel: RewardViewModel by viewModels()
    private lateinit var adapter: RewardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reward, container, false)

        adapter = RewardAdapter { reward ->
            if (reward.isUnlocked) {
                Toast.makeText(requireContext(), "보상: ${reward.title} 사용!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "조건 미달로 보상을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_reward)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        rewardViewModel.rewards.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return view
    }
}