package com.truong.movieapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelFactoryTest {
    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var factory: MainViewModelFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        factory = MainViewModelFactory(repository)
    }

    @Test
    fun `create should return MainViewModel`() {
        val viewModel = factory.create(MainViewModel::class.java)
        assertTrue(viewModel is MainViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create should throw IllegalArgumentException for unknown ViewModel class`() {
        class UnknownViewModel : ViewModel()

        factory.create(UnknownViewModel::class.java)
    }
}