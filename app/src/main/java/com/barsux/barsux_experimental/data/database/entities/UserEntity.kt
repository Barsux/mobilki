package com.barsux.barsux_experimental.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val email: String,
    val password: String?,
    val firstName: String,
    val secondName: String,
    val lastName: String,
    val birthDate: String,
    val sex: String,
    val driverId: String,
    val driverIdIssueDate: String,
    val avatarPath: String?,
    val driverIdPhotoPath: String?,
    val passportPhotoPath: String?,
    val isGoogleAccount: Boolean = false,
    val googleId: String? = null
)