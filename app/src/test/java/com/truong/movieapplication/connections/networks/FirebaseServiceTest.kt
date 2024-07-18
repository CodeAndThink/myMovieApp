package com.truong.movieapplication.connections.networks

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.truong.movieapplication.MockData
import com.truong.movieapplication.data.respository.FirebaseService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FirebaseServiceUnitTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseService: FirebaseService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        auth = mock(FirebaseAuth::class.java)
        firestore = mock(FirebaseFirestore::class.java)
        firebaseService = FirebaseService()
    }

    @Test
    fun login_successful() {
        val email = MockData.mockUser.email
        val password = MockData.mockUser.password
        val firebaseUser = mock(FirebaseUser::class.java)
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(auth.signInWithEmailAndPassword(email, password)).thenReturn(task)
        `when`(task.isSuccessful).thenReturn(true)
        `when`(auth.currentUser).thenReturn(firebaseUser)
        `when`(firebaseUser.uid).thenReturn("123456")

        firebaseService.login(email, password) { success, user, error ->
            assert(success)
            assert(user != null)
            assert(user?.id == "123456".hashCode().toLong())
            assert(user?.email == email)
            assert(user?.password == password)
            assert(error == null)
        }

        verify(auth).signInWithEmailAndPassword(email, password)
    }

    @Test
    fun login_failed() {
        val email = "test@example.com"
        val password = "password123"
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(auth.signInWithEmailAndPassword(email, password)).thenReturn(task)
        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.exception).thenReturn(Exception("Login failed"))

        firebaseService.login(email, password) { success, user, error ->
            assert(!success)
            assert(user == null)
            assert(error == "Login failed")
        }

        verify(auth).signInWithEmailAndPassword(email, password)
    }
}