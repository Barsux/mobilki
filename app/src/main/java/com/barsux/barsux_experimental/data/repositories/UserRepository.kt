package com.barsux.barsux_experimental.data.repositories

import com.barsux.barsux_experimental.data.database.dao.UserDao
import com.barsux.barsux_experimental.data.database.entities.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: UserEntity): Long = userDao.insertUser(user)
    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)
    suspend fun getUserById(id: Long): UserEntity? = userDao.getUserById(id)
    suspend fun clearUsers() = userDao.clearUsers()
}
