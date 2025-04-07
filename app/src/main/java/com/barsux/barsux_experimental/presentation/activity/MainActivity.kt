package com.barsux.barsux_experimental.presentation.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import com.barsux.barsux_experimental.presentation.fragments.SignInFragment
import com.barsux.barsux_experimental.presentation.fragments.SignInUpFragment
import com.barsux.barsux_experimental.presentation.fragments.OnboardingFragment
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment2
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment3
import com.barsux.barsux_experimental.presentation.fragments.SignUpFragment4
import com.barsux.barsux_experimental.presentation.fragments.NoConnectionFragment
import com.barsux.barsux_experimental.presentation.fragments.BaseFragment
import com.barsux.barsux_experimental.R
import com.barsux.barsux_experimental.common.AuthManager
import com.barsux.barsux_experimental.common.NetworkConnectionLiveData
import com.barsux.barsux_experimental.common.OnboardingManager

/**
 * Главная и единственная Activity в приложении.
 * Используется для управления навигацией между фрагментами.
 */


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!OnboardingManager.isOnboardingCompleted(this)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingFragment())
                .commit()
        } else if (AuthManager.getLoggedInEmail(this) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BaseFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInUpFragment())
                .commit()
        }
        observeNetworkChanges()
    }


    private fun observeNetworkChanges() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val builder = NetworkRequest.Builder()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                    if (currentFragment is NoConnectionFragment) {
                        supportFragmentManager.popBackStack()
                    }
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, NoConnectionFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }
}
