package com.barsux.barsux_experimental.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.barsux.barsux_experimental.data.database.entities.UserEntity
import com.barsux.barsux_experimental.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userRepository.getUserByEmail(email)
    }

    suspend fun insertUser(user: UserEntity): Long {
        return userRepository.insertUser(user)
    }
}
