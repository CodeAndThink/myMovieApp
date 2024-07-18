package com.truong.movieapplication.adapters.login

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.ui.login.LoginActivity
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java)

    private lateinit var authViewModel: LoginViewModel

    @Before
    fun setup() {
        val activity = activityRule.activity

        val preferencesHelper = SharedReferencesHelper(activity)
        val dao = UserDatabase.getDatabase(activity).userDao()
        val repository = LoginRepository(FirebaseService(), dao, preferencesHelper)
        val factory = LoginViewModelFactory(repository)
        authViewModel = ViewModelProvider(activity, factory)[LoginViewModel::class.java]
    }

    @Test
    fun testViewModelInitialization() {
        assertNotNull(authViewModel)
    }
}