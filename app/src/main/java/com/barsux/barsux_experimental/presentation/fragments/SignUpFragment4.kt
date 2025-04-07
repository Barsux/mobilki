package com.barsux.barsux_experimental.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.databinding.FragmentSignUpStep4Binding
import com.barsux.barsux_experimental.presentation.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SignUpFragment4 : Fragment() {

    private var _binding: FragmentSignUpStep4Binding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextButton.setOnClickListener {
            viewModel.register(viewLifecycleOwner.lifecycleScope) {
                Toast.makeText(requireContext(), "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show()

                // Можно переходить на главный экран или логин
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SignInFragment())
                    .commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
