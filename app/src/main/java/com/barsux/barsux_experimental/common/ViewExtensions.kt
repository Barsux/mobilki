package com.barsux.barsux_experimental.common

import android.content.res.Resources
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun View.setupBackButton(fragment: Fragment) {
    setOnClickListener {
        fragment.requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}