package com.barsux.barsux_experimental.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.barsux.barsux_experimental.data.database.entities.UserEntity
import com.barsux.barsux_experimental.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var email: String = ""
    var password: String = ""
    var firstName: String = ""
    var secondName: String = ""
    var lastName: String = ""
    var birthDate: String = ""
    var sex: String = ""
    var driverId: String = ""
    var driverIdIssueDate: String = ""
    var avatarPath: String? = null
    var driverIdPhotoPath: String? = null
    var passportPhotoPath: String? = null

    fun register(scope: CoroutineScope, onComplete: () -> Unit) {
        scope.launch {
            val user = UserEntity(
                email = email,
                password = password,
                firstName = firstName,
                secondName = secondName,
                lastName = lastName,
                birthDate = birthDate,
                sex = sex,
                driverId = driverId,
                driverIdIssueDate = driverIdIssueDate,
                avatarPath = avatarPath,
                driverIdPhotoPath = driverIdPhotoPath,
                passportPhotoPath = passportPhotoPath
            )

            val inserted = userRepository.insertUser(user)
            Log.d("SignUpViewModel", "✅ User inserted with id = $inserted")

            onComplete()
        }
    }
}
