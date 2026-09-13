package com.example.s8156366assignment2.ui.dashboard

import com.example.s8156366assignment2.data.model.DashboardResponse
import com.example.s8156366assignment2.data.model.ExerciseEntity
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
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AppRepository
    private lateinit var viewModel: DashboardViewModel

    private val sampleExercise = ExerciseEntity(
        exerciseName = "Push Up",
        muscleGroup = "Chest",
        equipment = "None",
        difficulty = "Beginner",
        caloriesBurnedPerHour = 300,
        description = "A basic bodyweight exercise."
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        assertTrue(viewModel.uiState.value is DashboardUiState.Loading)
    }

    @Test
    fun `successful fetch emits Success with entities`() = runTest(testDispatcher) {
        coEvery { repository.getDashboardData("fitness") } returns
                Result.success(DashboardResponse(entities = listOf(sampleExercise), entityTotal = 1))

        viewModel.loadDashboard("fitness")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Success)
        assertEquals(1, (state as DashboardUiState.Success).exercises.size)
        assertEquals("Push Up", state.exercises.first().exerciseName)
    }

    @Test
    fun `failed fetch emits Error with message`() = runTest(testDispatcher) {
        coEvery { repository.getDashboardData("fitness") } returns
                Result.failure(Exception("Failed to fetch dashboard (code 404)."))

        viewModel.loadDashboard("fitness")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Error)
        assertEquals("Failed to fetch dashboard (code 404).", (state as DashboardUiState.Error).message)
    }
}