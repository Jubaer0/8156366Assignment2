package com.example.s8156366assignment2.data.model

import java.io.Serializable

data class ExerciseEntity(
    val exerciseName: String,
    val muscleGroup: String,
    val equipment: String,
    val difficulty: String,
    val caloriesBurnedPerHour: Int,
    val description: String
) : Serializable