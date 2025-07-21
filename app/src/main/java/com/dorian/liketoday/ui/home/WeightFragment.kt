package com.dorian.liketoday.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dorian.liketoday.data.WeightEntry
import com.dorian.liketoday.databinding.FragmentWeightBinding
import com.dorian.liketoday.viewmodel.WeightViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.zip.Inflater

class WeightFragment : Fragment() {

    private var _binding: FragmentWeightBinding? = null
    private val binding get() = _binding!!

    private val weightViewModel: WeightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        binding.buttonSaveWeight.setOnClickListener {
            val selectedId = binding.radioGroupTime.checkedRadioButtonId
            val type = when(selectedId) {
                binding.radioMorning.id -> "morning"
                binding.radioEvening.id -> "evening"
                else -> null
            }

            val weight = binding.editWeight.text.toString().toFloatOrNull()

            if(type != null && weight != null){
                val entry = WeightEntry(date = date, type = type, weight = weight)
                weightViewModel.insert(entry)
                Toast.makeText(requireContext(), "몸무게 저장 완료!", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "모든 값을 정확히 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}