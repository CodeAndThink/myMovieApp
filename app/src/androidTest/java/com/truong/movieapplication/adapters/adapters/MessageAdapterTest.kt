package com.truong.movieapplication.adapters.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.truong.movieapplication.R
import com.truong.movieapplication.data.models.Message
import com.truong.movieapplication.ui.adapters.MessageAdapter
import com.truong.movieapplication.ui.mainscreen.MainActivity
import com.truong.movieapplication.ui.mainscreen.notification.MessageDetailActivity
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MessageAdapterTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var adapter: MessageAdapter

    private val mockMessage = Message("Test Message", "Test Title", "info", 0, "2024-01-01")

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val recyclerView: RecyclerView = activity.findViewById(R.id.message_list)
                adapter = MessageAdapter(listOf(mockMessage))
                recyclerView.adapter = adapter
            }
        }
    }

    @Test
    fun testItemCount() {
        activityScenarioRule.scenario.onActivity { activity ->
            val recyclerView: RecyclerView = activity.findViewById(R.id.message_list)
            assertEquals(1, recyclerView.adapter?.itemCount)
        }
    }

    @Test
    fun testViewHolderBinding() {
        activityScenarioRule.scenario.onActivity { activity ->
            val recyclerView: RecyclerView = activity.findViewById(R.id.message_list)
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(0) as MessageAdapter.MessageViewHolder
            val titleView = viewHolder.itemView.findViewById<TextView>(R.id.message_title)
            val contextView = viewHolder.itemView.findViewById<TextView>(R.id.message_context)

            assertEquals("Test Message", titleView.text.toString())
            assertEquals("info", contextView.text.toString())
        }
    }

    @Test
    fun testItemViewClick() {
        Intents.init()

        onView(withId(R.id.message_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MessageAdapter.MessageViewHolder>(0, click()))

        Intents.intended(allOf(
            hasComponent(MessageDetailActivity::class.java.name),
            hasExtra("message", "Test Message"),
            hasExtra("title", "Test Title")
        ))

        Intents.release()
    }
}