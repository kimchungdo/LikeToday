package com.dorian.liketoday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dorian.liketoday.databinding.FragmentMyLogsBinding

class MyLogsFragment : Fragment() {

    private var binding: FragmentMyLogsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyLogsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyLogsFragment()
    }
}