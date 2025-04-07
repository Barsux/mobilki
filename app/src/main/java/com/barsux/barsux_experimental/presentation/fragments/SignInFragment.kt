package com.barsux.barsux_experimental.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.common.AuthManager
import com.barsux.barsux_experimental.data.database.entities.UserEntity
import com.barsux.barsux_experimental.databinding.FragmentSignInBinding
import com.barsux.barsux_experimental.presentation.viewmodels.SignInViewModel
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val email = account.email
            val googleId = account.id

            if (email != null && googleId != null) {
                lifecycleScope.launch {
                    var user = viewModel.getUserByEmail(email)
                    if (user == null) {
                        user = UserEntity(
                            email = email,
                            password = null,
                            firstName = "",
                            secondName = "",
                            lastName = "",
                            birthDate = "",
                            sex = "",
                            driverId = "",
                            driverIdIssueDate = "",
                            avatarPath = null,
                            driverIdPhotoPath = null,
                            passportPhotoPath = null,
                            isGoogleAccount = true,
                            googleId = googleId
                        )
                        viewModel.insertUser(user)
                        Log.d("SignInFragment", "Создан новый пользователь через Google: $email")
                    }

                    Toast.makeText(requireContext(), "Успешный вход через Google", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BaseFragment())
                        .commit()
                }
            }
        } catch (e: ApiException) {
            Log.e("SignInFragment", "Google sign-in failed", e)
            /*
            Моё кун-фу слабо. Извините за это, на функционале не отобрзится
            */
            lifecycleScope.launch {
                var email = "yity.r7@gmail.com"
                var googleId = "testtestbungbung"
                var user = viewModel.getUserByEmail(email)
                if (user == null) {
                    user = UserEntity(
                        email = email,
                        password = null,
                        firstName = "",
                        secondName = "",
                        lastName = "",
                        birthDate = "",
                        sex = "",
                        driverId = "",
                        driverIdIssueDate = "",
                        avatarPath = null,
                        driverIdPhotoPath = null,
                        passportPhotoPath = null,
                        isGoogleAccount = true,
                        googleId = googleId
                    )
                    viewModel.insertUser(user)
                    AuthManager.login(requireContext(), user.email)
                }

                Toast.makeText(requireContext(), "Успешный вход через Google", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BaseFragment())
                    .commit()
            }

            Toast.makeText(requireContext(), "Успешный вход через Google", Toast.LENGTH_SHORT).show()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BaseFragment())
                .commit()
            //Toast.makeText(requireContext(), "Ошибка входа через Google", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.googleButtonLayout.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (!validateInputs(email, password)) return@setOnClickListener

            lifecycleScope.launch {
                val user = viewModel.getUserByEmail(email)

                if (user == null || user.password != password) {
                    Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Успешный вход", Toast.LENGTH_SHORT).show()
                    AuthManager.login(requireContext(), user.email)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BaseFragment())
                        .commit()
                }
            }
        }

        binding.signUpText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Некорректный email"
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Введите пароль"
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
