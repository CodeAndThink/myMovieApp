package com.truong.movieapplication.data.respository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.truong.movieapplication.data.connections.network.AuthServices
import com.truong.movieapplication.data.models.Message
import com.truong.movieapplication.data.models.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseService : AuthServices {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun login(email: String, password: String, callback: (Boolean, User?, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val user = firebaseUser?.let {
                        User(id = it.uid.hashCode().toLong(), email = email, password = password)
                    }
                    callback(true, user, null)
                } else {
                    callback(false, null, task.exception?.message)
                }
            }
    }

    override fun register(email : String, displayName: String, password: String, callback: (Boolean, User?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val user = User(id = firebaseUser?.uid?.hashCode()?.toLong() ?: 0L, email = email, password = password)

                    val userData = hashMapOf(
                        "display_name" to displayName,
                        "email" to email,
                        "password" to password,
                        "avatar" to "",
                        "wish_list" to listOf<Long>(),
                        "dob" to "",
                        "gender" to "",
                        "address" to "",
                        "phone" to ""
                    )

                    firestore.collection("users").document(email)
                        .set(userData)
                        .addOnSuccessListener {
                            callback(true, user, null)
                        }
                        .addOnFailureListener { e ->
                            callback(false, null, e.message)
                        }
                } else {
                    callback(false, null, authTask.exception?.message)
                }
            }
    }

    override fun getUserData(email: String, callback: (Boolean, User?, String?) -> Unit) {
        firestore.collection("users").document(email)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.data
                    val user = User(
                        id = email.hashCode().toLong(),
                        display_name = userData?.get("display_name").toString(),
                        email = userData?.get("email").toString(),
                        password = userData?.get("password").toString(),
                        avatar = userData?.get("avatar").toString(),
                        wish_list = userData?.get("wish_list") as? List<Long>,
                        dob = userData?.get("dob").toString(),
                        gender = userData?.get("gender") as? Boolean,
                        address = userData?.get("address").toString(),
                        phone = userData?.get("phone").toString()
                    )
                    callback(true, user, null)
                } else {
                    callback(false, null, "User not found")
                }
            }
            .addOnFailureListener { e ->
                callback(false, null, e.message)
            }
    }

    override fun updateUserData(user: User, callback: (Boolean, String?) -> Unit) {
        val userData = hashMapOf(
            "display_name" to user.display_name,
            "avatar" to user.avatar,
            "wish_list" to user.wish_list,
            "email" to user.email,
            "dob" to user.dob,
            "gender" to user.gender,
            "address" to user.address,
            "phone" to user.phone
        )

        firestore.collection("users").document(user.email)
            .set(userData)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { e ->
                callback(false, e.message)
            }
    }

    override fun updateWishList(email: String, wishList: List<Long>, callback: (Boolean, String?) -> Unit) {
        val userDocument = firestore.collection("users").document(email)

        userDocument.update("wish_list", wishList)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { e ->
                callback(false, e.message)
            }
    }

    override fun deleteWishListByMovieId(email: String, wishList: List<Long>, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun changePassword(
        oldPassword: String,
        newPassword: String,
        callback: (Boolean, String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun logout(callback: (Boolean, String?) -> Unit) {

    }

    override suspend fun getMessage(): List<Message> = suspendCancellableCoroutine { continuation ->
        firestore.collection("messages")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val messages = querySnapshot.documents.mapNotNull {
                        val messageData = it.data
                        if (messageData != null) {
                            val id = it.id
                            val title = messageData["title"]?.toString() ?: ""
                            val message = messageData["message"]?.toString() ?: ""
                            val type = messageData["type"]?.toString()?.toIntOrNull() ?: 0
                            val date = messageData["date"]?.toString() ?: ""

                            Message(id, title, message, type, date)
                        } else {
                            null
                        }
                    }
                    continuation.resume(messages)
                } else {
                    continuation.resume(emptyList())
                }
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }
}