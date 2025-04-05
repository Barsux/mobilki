package com.barsux.barsux_experimental.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.barsux.barsux_experimental.R

/**
 * Экран авторизации и регистрации пользователя.
 * Используется для входа в систему или создания нового аккаунта.
 */
class SignUpFragment4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sign_up_step4, container, false)
    }

    // Здесь ты можешь добавить обработку кнопок, вводов и т.д.
}