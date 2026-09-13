package com.example.s8156366assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s8156366assignment2.data.model.ExerciseEntity
import com.example.s8156366assignment2.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private val onItemClick: (ExerciseEntity) -> Unit
) : RecyclerView.Adapter<ExerciseViewHolder>() {

    private var exerciseList: List<ExerciseEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExerciseViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exerciseList[position])
    }

    override fun getItemCount(): Int = exerciseList.size

    fun updateData(newList: List<ExerciseEntity>) {
        exerciseList = newList
        notifyDataSetChanged()
    }
}