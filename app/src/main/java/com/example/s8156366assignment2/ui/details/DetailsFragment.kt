package com.example.s8156366assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.s8156366assignment2.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exercise = args.exercise

        binding.detailsExerciseNameTextView.text = exercise.exerciseName
        binding.detailsMuscleGroupTextView.text = exercise.muscleGroup
        binding.detailsEquipmentTextView.text = exercise.equipment
        binding.detailsDifficultyTextView.text = exercise.difficulty
        binding.detailsCaloriesTextView.text = "${exercise.caloriesBurnedPerHour} kcal/hour"
        binding.detailsDescriptionTextView.text = exercise.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}