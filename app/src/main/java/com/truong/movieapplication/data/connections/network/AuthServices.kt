package com.truong.movieapplication.data.connections.network

import com.truong.movieapplication.data.models.User

interface AuthServices {
    fun login(email: String, password: String, callback: (Boolean, User?, String?) -> Unit)
    fun register(email : String, displayName: String, password: String, callback: (Boolean, User?, String?) -> Unit)
    fun getUserData(email: String, callback: (Boolean, User?, String?) -> Unit)
    fun updateUserData(user: User, callback: (Boolean, String?) -> Unit)
    fun updateWishList(email: String, wishList: List<Long>, callback: (Boolean, String?) -> Unit)
    fun changePassword(oldPassword: String, newPassword: String, callback: (Boolean, String?) -> Unit)
    fun logout(callback: (Boolean, String?) -> Unit)
}