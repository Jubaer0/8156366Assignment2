package com.example.s8156366assignment2.ui.login

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
import com.example.s8156366assignment2.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            viewModel.onLoginClicked(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state -> render(state) }
            }
        }
    }

    private fun render(state: LoginUiState) {
        binding.loginProgressBar.visibility = if (state is LoginUiState.Loading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = state !is LoginUiState.Loading

        when (state) {
            is LoginUiState.Error -> {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = state.message
            }
            is LoginUiState.Success -> {
                binding.errorTextView.visibility = View.GONE
                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(state.keypass)
                findNavController().navigate(action)
                viewModel.resetState()
            }
            LoginUiState.Idle, LoginUiState.Loading -> binding.errorTextView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}