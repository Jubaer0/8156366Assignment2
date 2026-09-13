package com.example.s8156366assignment2.data.repository

import com.example.s8156366assignment2.data.model.DashboardResponse
import com.example.s8156366assignment2.data.model.LoginRequest
import com.example.s8156366assignment2.data.model.LoginResponse
import com.example.s8156366assignment2.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(studentId: String, firstName: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username = studentId, password = firstName))
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Empty response body from server."))
            } else {
                Result.failure(Exception("Login failed (code ${response.code()}). Please check your credentials."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDashboardData(keypass: String): Result<DashboardResponse> {
        return try {
            val response = apiService.getDashboard(keypass)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Empty dashboard response."))
            } else {
                Result.failure(Exception("Failed to fetch dashboard (code ${response.code()})."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}