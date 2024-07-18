package com.truong.movieapplication.connections.locals

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.truong.movieapplication.data.connections.local.UserDAO
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.models.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var db: UserDatabase
    private lateinit var userDao: UserDAO

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndGetAllUsers() = runBlocking {
        val user1 = User(id = 1L, display_name = "John Doe", email = "john@example.com", password = "password")
        val user2 = User(id = 2L, display_name = "Jane Smith", email = "jane@example.com", password = "password")
        userDao.insertAllUser(user1, user2)
        val users = userDao.getAllUser()
        assertEquals(2, users.size)
        assertTrue(users.contains(user1))
        assertTrue(users.contains(user2))
    }

    @Test
    fun testDeleteUser() = runBlocking {
        val user = User(id = 1L, display_name = "John Doe", email = "john@example.com", password = "password")
        userDao.insertUser(user)
        userDao.deleteUser(user)
        val users = userDao.getAllUser()
        assertTrue(users.isEmpty())
    }

    @Test
    fun testDeleteAllUsers() = runBlocking {
        val user1 = User(id = 1L, display_name = "John Doe", email = "john@example.com", password = "password")
        val user2 = User(id = 2L, display_name = "Jane Smith", email = "jane@example.com", password = "password")
        userDao.insertAllUser(user1, user2)
        userDao.deleteAllUser()
        val users = userDao.getAllUser()
        assertTrue(users.isEmpty())
    }
}