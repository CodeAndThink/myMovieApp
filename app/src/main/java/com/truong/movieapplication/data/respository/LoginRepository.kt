package com.truong.movieapplication.data.respository

import com.truong.movieapplication.data.connections.local.UserDAO
import com.truong.movieapplication.data.connections.network.AuthServices
import com.truong.movieapplication.data.models.User

class LoginRepository {

    private lateinit var authServices: AuthServices
    private lateinit var sharedReferencesHelper: SharedReferencesHelper
    private lateinit var userDAO: UserDAO

    constructor(authServices: AuthServices, userDAO: UserDAO) {
        this.authServices = authServices
        this.userDAO = userDAO
    }

    constructor(authServices: AuthServices, userDAO: UserDAO, sharedReferencesHelper: SharedReferencesHelper) {
        this.authServices = authServices
        this.sharedReferencesHelper = sharedReferencesHelper
        this.userDAO = userDAO
    }

    fun login(email: String, password: String, callback: (Boolean, User?, String?) -> Unit) {
        authServices.login(email, password, callback)
    }

    fun register(email : String, displayName: String, password: String, callback: (Boolean, User?, String?) -> Unit) {
        authServices.register(email, displayName, password, callback)
    }

    fun getUserData(email: String, callback: (Boolean, User?, String?) -> Unit) {
        authServices.getUserData(email, callback)
    }

    fun updateUserData(user: User, callback: (Boolean, String?) -> Unit) {
        authServices.updateUserData(user, callback)
    }

    fun updateWishList(email: String, wishList: MutableList<Long>, callback: (Boolean, String?) -> Unit) {
        authServices.updateWishList(email, wishList, callback)
    }

    fun saveStartTime(time: Long) {
        sharedReferencesHelper.setLong("start_time", time)
    }

    fun getStartTime(): Long {
        return sharedReferencesHelper.getLong("start_time")
    }

    fun saveEmail(email: String) {
        sharedReferencesHelper.setString("email", email)
    }

    fun getEmail(): String? {
        return sharedReferencesHelper.getString("email")
    }

    fun getAllUsers(): List<User> {
        return userDAO.getAllUser()
    }

    fun insertUser(user: User) {
        userDAO.insertUser(user)
    }

    fun deleteAllUser(user: User) {
        userDAO.deleteAllUser()
    }

    fun deleteUser(user: User) {
        userDAO.deleteUser(user)
    }
}
