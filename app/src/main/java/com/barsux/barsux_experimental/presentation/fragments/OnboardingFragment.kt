package com.barsux.barsux_experimental.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.presentation.adapters.OnboardingAdapter
import com.barsux.barsux_experimental.presentation.adapters.OnboardingItem
import com.google.android.material.button.MaterialButton
import androidx.fragment.app.commit
import com.barsux.barsux_experimental.presentation.fragments.SignInUpFragment

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsContainer: LinearLayout
    private lateinit var nextButton: MaterialButton
    private lateinit var skipButton: TextView

    private val items = listOf(
        OnboardingItem(R.drawable.onboarding1, "Аренда автомобилей", "Открой для себя удобный и доступный способ передвижения"),
        OnboardingItem(R.drawable.onboarding2, "Безопасно и удобно", "Арендуй автомобиль и наслаждайся его удобством"),
        OnboardingItem(R.drawable.onboarding3, "Лучшие предложения", "Выбирай среди сотен авто", isLast = true)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.viewPager)
        dotsContainer = view.findViewById(R.id.dotsContainer)
        dotsContainer.removeAllViews()
        nextButton = view.findViewById(R.id.nextButton)

        viewPager.adapter = OnboardingAdapter(items)
        dotsContainer.post {
            updateDots(0)
        }

        skipButton = view.findViewById(R.id.skipButton)

        skipButton.setOnClickListener {
            navigateToSignIn()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
                nextButton.text = if (items[position].isLast) "Поехали" else "Далее"
            }
        })

        nextButton.setOnClickListener {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < items.size) {
                viewPager.currentItem = nextItem
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SignInUpFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    private fun navigateToSignIn() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, SignInUpFragment())
            addToBackStack(null)
        }
    }
    private fun updateDots(position: Int) {
        dotsContainer.removeAllViews()
        for (i in items.indices) {
            val dot = View(requireContext())
            val params = LinearLayout.LayoutParams(
                if (i == position) 42.dp else 16.dp,
                8.dp
            ).apply {
                marginEnd = 8.dp
            }
            dot.layoutParams = params
            dot.setBackgroundResource(
                if (i == position) R.drawable.active_dot else R.drawable.inactive_dot
            )
            dotsContainer.addView(dot)
        }
    }

    private val Int.dp: Int get() =
        (this * resources.displayMetrics.density).toInt()
}
