package com.barsux.barsux_experimental.presentation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.barsux.barsux_experimental.databinding.FragmentSignUpBinding
import com.barsux.barsux_experimental.presentation.viewmodels.SignUpViewModel
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment2
import com.barsux.barsux_experimental.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextButton.setOnClickListener {
            if (validateInputs()) {
                viewModel.email = binding.emailEditText.text.toString()
                viewModel.password = binding.passwordEditText.text.toString()

                // Переход ко второму фрагменту
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SignUpFragment2())
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.emailEditText.doAfterTextChanged { binding.emailInputLayout.error = null }
        binding.passwordEditText.doAfterTextChanged { binding.passwordInputLayout.error = null }
        binding.confirmPasswordEditText.doAfterTextChanged { binding.confirmPasswordInputLayout.error = null }
    }

    private fun validateInputs(): Boolean {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()
        val isChecked = binding.termsCheckBox.isChecked

        var isValid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Некорректный email"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Введите пароль"
            isValid = false
        }

        if (confirmPassword != password) {
            binding.confirmPasswordInputLayout.error = "Пароли не совпадают"
            isValid = false
        }

        if (!isChecked) {
            binding.termsErrorText.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.termsErrorText.visibility = View.GONE
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
