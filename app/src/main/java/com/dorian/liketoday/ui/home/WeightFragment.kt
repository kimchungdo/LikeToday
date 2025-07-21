package com.dorian.liketoday.ui.home

import android.app.DatePickerDialog
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
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE

        // 기본값 설정
        binding.editDate.setText(today.format(formatter))

        // DatePickerDialog 연결
        binding.editDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    binding.editDate.setText(selectedDate.format(formatter))
                },
                today.year, today.monthValue - 1, today.dayOfMonth
            )
            datePicker.show()
        }

        // 저장 버튼
        binding.buttonSaveWeight.setOnClickListener {
            val selectedId = binding.radioGroupTime.checkedRadioButtonId
            val type = when (selectedId) {
                binding.radioMorning.id -> "morning"
                binding.radioEvening.id -> "evening"
                else -> null
            }

            val dateInput = binding.editDate.text.toString().trim()
            val weight = binding.editWeight.text.toString().toFloatOrNull()

            if (type != null && weight != null && dateInput.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                val entry = WeightEntry(date = dateInput, type = type, weight = weight)
                weightViewModel.insert(entry)
                Toast.makeText(requireContext(), "몸무게 저장 완료!", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "입력값을 확인하세요 (날짜, 몸무게, 시간대)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}