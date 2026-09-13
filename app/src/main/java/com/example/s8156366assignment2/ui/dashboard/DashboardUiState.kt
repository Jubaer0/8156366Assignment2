package com.example.s8156366assignment2.ui.dashboard

import com.example.s8156366assignment2.data.model.ExerciseEntity

sealed class DashboardUiState {
    data object Loading : DashboardUiState()
    data class Success(val exercises: List<ExerciseEntity>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}