package com.truong.movieapplication.models

import com.truong.movieapplication.data.models.Message
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MessageTest {

    @Test
    fun testMessageInitialization() {
        val message = Message(
            id = "1",
            title = "Test Title",
            message = "This is a test message",
            type = 1,
            date = "2024-07-15"
        )

        assertEquals("1", message.id)
        assertEquals("Test Title", message.title)
        assertEquals("This is a test message", message.message)
        assertEquals(1, message.type)
        assertEquals("2024-07-15", message.date)
    }

    @Test
    fun testMessageEquality() {
        val message1 = Message(
            id = "1",
            title = "Test Title",
            message = "This is a test message",
            type = 1,
            date = "2024-07-15"
        )

        val message2 = Message(
            id = "1",
            title = "Test Title",
            message = "This is a test message",
            type = 1,
            date = "2024-07-15"
        )

        assertEquals(message1, message2)
    }

    @Test
    fun testMessageInequality() {
        val message1 = Message(
            id = "1",
            title = "Test Title",
            message = "This is a test message",
            type = 1,
            date = "2024-07-15"
        )

        val message2 = Message(
            id = "2",
            title = "Test Title",
            message = "This is a test message",
            type = 1,
            date = "2024-07-15"
        )

        assertNotEquals(message1, message2)
    }
}