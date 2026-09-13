package com.example.s8156366assignment2.ui.login

import com.example.s8156366assignment2.data.model.LoginResponse
import com.example.s8156366assignment2.data.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AppRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `empty username shows validation error`() = runTest(testDispatcher) {
        viewModel.onLoginClicked(studentId = "", firstName = "Sam")
        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
    }

    @Test
    fun `successful login emits Success with keypass`() = runTest(testDispatcher) {
        coEvery { repository.login("12345678", "Sam") } returns Result.success(LoginResponse("fitness"))
        viewModel.onLoginClicked("12345678", "Sam")
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Success)
        assertEquals("fitness", (state as LoginUiState.Success).keypass)
    }

    @Test
    fun `failed login emits Error with message`() = runTest(testDispatcher) {
        coEvery { repository.login("12345678", "Sam") } returns Result.failure(Exception("Login failed (code 401). Please check your credentials."))
        viewModel.onLoginClicked("12345678", "Sam")
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
    }
}