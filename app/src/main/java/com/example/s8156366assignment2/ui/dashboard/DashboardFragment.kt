package com.example.s8156366assignment2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s8156366assignment2.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private val args: DashboardFragmentArgs by navArgs()

    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExerciseAdapter { exercise ->
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToDetailsFragment(exercise)
            findNavController().navigate(action)
        }

        binding.dashboardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dashboardRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state -> render(state) }
            }
        }

        viewModel.loadDashboard(args.keypass)
    }

    private fun render(state: DashboardUiState) {
        binding.dashboardProgressBar.visibility =
            if (state is DashboardUiState.Loading) View.VISIBLE else View.GONE
        binding.dashboardRecyclerView.visibility =
            if (state is DashboardUiState.Success) View.VISIBLE else View.GONE
        binding.dashboardErrorTextView.visibility =
            if (state is DashboardUiState.Error) View.VISIBLE else View.GONE

        when (state) {
            is DashboardUiState.Success -> adapter.updateData(state.exercises)
            is DashboardUiState.Error -> binding.dashboardErrorTextView.text = state.message
            DashboardUiState.Loading -> Unit
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}