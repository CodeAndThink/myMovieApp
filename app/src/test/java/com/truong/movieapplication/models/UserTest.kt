package com.truong.movieapplication.models

import com.truong.movieapplication.MockData
import com.truong.movieapplication.data.models.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {
    @Test
    fun testConstructorAndGetters() {
        val user = MockData.mockUser

        assertEquals(1, user.id)
        assertEquals("John Doe", user.display_name)
        assertEquals("john.doe@example.com", user.email)
        assertEquals("password123", user.password)
        assertEquals("avatar_url", user.avatar)
        assertEquals(listOf(1L, 2L), user.wish_list)
        assertEquals("1990-01-01", user.dob)
        assertEquals(true, user.gender)
        assertEquals("123 Street, City", user.address)
        assertEquals("+1234567890", user.phone)
    }
}