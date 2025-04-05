package com.barsux.barsux_experimental.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.barsux.barsux_experimental.presentation.fragments.SignInFragment
import com.barsux.barsux_experimental.presentation.fragments.SignInUpFragment
import com.barsux.barsux_experimental.presentation.fragments.OnboardingFragment
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment2
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment3
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment4
import com.barsux.barsux_experimental.R

/**
 * Главная и единственная Activity в приложении.
 * Используется для управления навигацией между фрагментами.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Если первый запуск, добавляем стартовый фрагмент
        if (savedInstanceState == null) {
            launchFragment(SignInFragment())
        }
    }

    /**
     * Функция для запуска фрагмента, заменяя текущий.
     * @param fragment Фрагмент, который будет показан
     */
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
