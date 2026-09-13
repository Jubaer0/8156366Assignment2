package com.example.s8156366assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8156366assignment2.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    private var loadedKeypass: String? = null

    fun loadDashboard(keypass: String) {
        if (loadedKeypass == keypass && _uiState.value !is DashboardUiState.Error) return

        loadedKeypass = keypass
        _uiState.value = DashboardUiState.Loading

        viewModelScope.launch {
            repository.getDashboardData(keypass)
                .onSuccess { response ->
                    _uiState.value = DashboardUiState.Success(response.entities)
                }
                .onFailure { throwable ->
                    _uiState.value = DashboardUiState.Error(
                        throwable.message ?: "Unable to load dashboard. Please try again."
                    )
                }
        }
    }
}