package com.example.s8156366assignment2.ui.login

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
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginClicked(studentId: String, firstName: String) {
        val trimmedId = studentId.trim()
        val trimmedName = firstName.trim()

        if (trimmedId.isEmpty() || trimmedName.isEmpty()) {
            _uiState.value = LoginUiState.Error("Username and password cannot be empty.")
            return
        }

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            repository.login(trimmedId, trimmedName)
                .onSuccess { response -> _uiState.value = LoginUiState.Success(response.keypass) }
                .onFailure { throwable ->
                    _uiState.value = LoginUiState.Error(throwable.message ?: "An unknown error occurred. Please try again.")
                }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

}