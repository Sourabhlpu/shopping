package com.example.shopping.createclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shopping.R
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.databinding.FragmentCreateClientBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateClientFragment : Fragment() {

    companion object {
        val uiToolbar: UIToolbar
            get() = UIToolbar(
                showSpinner = false,
                showTitle = true,
                showRightAction = false,
                isTopNav = false
            )
    }

    private val binding get() = _binding!!

    private var _binding: FragmentCreateClientBinding? = null

    private val viewModel: CreateClientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_client, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInputFieldListeners()
        setupUI()
    }

    private fun setupUI() {
        observeViewStateUpdates()
    }

    private fun setupInputFieldListeners() {
        val nameInput = binding.itemName.value
        val emailInput = binding.itemEmail.value
        nameInput.addTextChangedListener { viewModel.onEvent(CreateClientEvent.NameInput(it?.toString().orEmpty()))}
        emailInput.addTextChangedListener { viewModel.onEvent(CreateClientEvent.EmailInput(it?.toString().orEmpty()))}
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(state: CreateClientState) {
        binding.btnSave.isEnabled = state.isFormValid
        binding.progressBar.isVisible = state.isSubmitting
        handleFailures(state.failure)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return
        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}