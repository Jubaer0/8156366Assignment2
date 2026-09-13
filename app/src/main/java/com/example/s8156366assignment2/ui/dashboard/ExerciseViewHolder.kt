package com.example.s8156366assignment2.ui.dashboard

import androidx.recyclerview.widget.RecyclerView
import com.example.s8156366assignment2.data.model.ExerciseEntity
import com.example.s8156366assignment2.databinding.ItemExerciseBinding

class ExerciseViewHolder(
    private val binding: ItemExerciseBinding,
    private val onItemClick: (ExerciseEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(exercise: ExerciseEntity) {
        binding.exerciseNameTextView.text = exercise.exerciseName
        binding.muscleGroupTextView.text = "Muscle group: ${exercise.muscleGroup}"
        binding.difficultyTextView.text = "Difficulty: ${exercise.difficulty}"
        binding.caloriesTextView.text = "Calories/hour: ${exercise.caloriesBurnedPerHour}"

        binding.root.setOnClickListener { onItemClick(exercise) }
    }
}