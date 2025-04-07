package com.barsux.barsux_experimental.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.databinding.FragmentSignInUpBinding
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment
import com.barsux.barsux_experimental.presentation.fragments.SignInFragment

/**
 * Экран авторизации и регистрации пользователя.
 * Используется для входа в систему или создания нового аккаунта.
 */
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInUpFragment : Fragment() {

    private var _binding: FragmentSignInUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signInButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.signUpButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}
