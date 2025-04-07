package com.barsux.barsux_experimental.presentation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.databinding.FragmentSignUpStep2Binding
import com.barsux.barsux_experimental.presentation.viewmodels.SignUpViewModel
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment
import com.barsux.barsux_experimental.common.setupBackButton
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment2 : Fragment() {

    private var _binding: FragmentSignUpStep2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextStepButton.setOnClickListener {
            if (validateInputs()) {
                viewModel.firstName = binding.firstNameEditText.text.toString()
                viewModel.secondName = binding.middleNameEditText.text.toString()
                viewModel.lastName = binding.lastNameEditText.text.toString()
                viewModel.birthDate = binding.birthdayEditText.text.toString()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SignUpFragment3())
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Сброс ошибок при вводе
        binding.backButton.setupBackButton(this)
        binding.firstNameEditText.doAfterTextChanged { binding.firstNameInputLayout.error = null }
        binding.middleNameEditText.doAfterTextChanged { binding.middleNameEditText.error = null }
        binding.lastNameEditText.doAfterTextChanged { binding.lastNameInputLayout.error = null }
        binding.birthdayEditText.doAfterTextChanged { binding.birthdayEditText.error = null }

        // Обработчик выбора даты
        binding.birthdayInputLayout.setStartIconOnClickListener {
            showDatePicker()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (binding.firstNameEditText.text.isNullOrBlank()) {
            binding.firstNameInputLayout.error = "Введите имя"
            isValid = false
        }
        if (binding.middleNameEditText.text.isNullOrBlank()) {
            binding.middleNameInputLayout.error = "Введите отчество"
            isValid = false
        }
        if (binding.lastNameEditText.text.isNullOrBlank()) {
            binding.lastNameInputLayout.error = "Введите фамилию"
            isValid = false
        }

        val birthDateStr = binding.birthdayEditText.text.toString()
        val birthDate = try {
            dateFormat.parse(birthDateStr)
        } catch (e: Exception) {
            null
        }

        if (birthDate == null) {
            binding.birthdayInputLayout.error = "Введите корректную дату"
            isValid = false
        } else if (birthDate.after(Date())) {
            binding.birthdayInputLayout.error = "Дата не может быть в будущем"
            isValid = false
        }
        if (binding.genderGroup.checkedRadioButtonId == -1) {
            binding.sexErrorText.text = "Выберите пол"
            binding.sexErrorText.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.sexErrorText.visibility = View.GONE
        }
        return isValid
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                binding.birthdayEditText.setText(dateFormat.format(selectedDate.time))
                binding.birthdayInputLayout.error = null
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = System.currentTimeMillis()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
