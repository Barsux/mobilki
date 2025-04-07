package com.barsux.barsux_experimental.presentation.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.databinding.FragmentSignUpStep3Binding
import com.barsux.barsux_experimental.presentation.viewmodels.SignUpViewModel
import com.barsux.barsux_experimental.common.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment3 : Fragment() {

    private var _binding: FragmentSignUpStep3Binding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels()

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var avatarUri: Uri? = null
    private var driverLicenseUri: Uri? = null
    private var passportUri: Uri? = null

    private val pickAvatar =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                avatarUri = it
                viewModel.avatarPath = it.toString()

                binding.avatarPlaceholder.setImageURI(it)
                binding.avatarPlaceholder.strokeWidth = 3f
                binding.avatarPlaceholder.strokeColor = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.green_success)
                )
                binding.addAvatarIcon.visibility = View.GONE
            }
        }

    private val pickDriverLicense =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                driverLicenseUri = it
                viewModel.driverIdPhotoPath = it.toString()
                binding.uploadLicensePhotoButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.green_border)
                binding.driverIdText.text = "Загружено"
                binding.uploadDriverId.visibility = View.GONE
            }
        }

    private val pickPassport =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                passportUri = it
                viewModel.passportPhotoPath = it.toString()
                binding.uploadPassportPhotoButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.green_border)
                binding.idText.text = "Загружено"
                binding.uploadId.visibility = View.GONE
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.licenseNumberEditText.doAfterTextChanged {
            binding.licenseNumberInputLayout.error = null
        }

        binding.issueDateEditText.setOnClickListener {
            showDatePicker()
        }

        binding.avatarPlaceholder.setOnClickListener {
            pickAvatar.launch("image/*")
        }

        binding.uploadLicensePhotoButton.setOnClickListener {
            pickDriverLicense.launch("image/*")
        }

        binding.uploadPassportPhotoButton.setOnClickListener {
            pickPassport.launch("image/*")
        }

        binding.nextButton.setOnClickListener {
            if (validateInputs()) {
                viewModel.driverId = binding.licenseNumberEditText.text.toString()
                viewModel.driverIdIssueDate = binding.issueDateEditText.text.toString()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SignUpFragment4())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val licenseNumber = binding.licenseNumberEditText.text.toString()
        val dateStr = binding.issueDateEditText.text.toString()

        var isValid = true

        if (licenseNumber.isEmpty()) {
            binding.licenseNumberInputLayout.error = "Введите номер ВУ"
            isValid = false
        }

        if (dateStr.isEmpty()) {
            binding.issueDateInputLayout.error = "Введите дату выдачи"
            isValid = false
            val params = binding.licensePhotoLabel.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 8.dpToPx()
            binding.licensePhotoLabel.layoutParams = params
        } else {
            try {
                val selectedDate = dateFormat.parse(dateStr)
                if (selectedDate != null && selectedDate.after(Calendar.getInstance().time)) {
                    binding.issueDateInputLayout.error = "Дата не может быть в будущем"
                    isValid = false
                }
            } catch (e: Exception) {
                binding.issueDateInputLayout.error = "Неверный формат даты"
                isValid = false
            }
        }

        if (viewModel.driverIdPhotoPath == null) {
            binding.uploadDriverId.visibility = View.VISIBLE
            val params = binding.passportPhotoLabel.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 8.dpToPx()
            binding.passportPhotoLabel.layoutParams = params
            isValid = false
        }

        if (viewModel.passportPhotoPath == null) {
            binding.uploadId.visibility = View.VISIBLE
            isValid = false
        }

        return isValid
    }

    private fun showDatePicker() {
        val now = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                binding.issueDateEditText.setText(dateFormat.format(calendar.time))
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = System.currentTimeMillis()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
