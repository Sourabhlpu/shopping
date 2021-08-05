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
import androidx.navigation.fragment.findNavController
import com.example.shopping.R
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.databinding.FragmentCreateClientBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        listenToGenderButton()
        listenToStatusButton()
        listenToSaveButton()
        observeViewStateUpdates()
    }

    private fun listenToSaveButton() {
        binding.btnSave.setOnClickListener {
            viewModel.onEvent(CreateClientEvent.SaveClient)
        }
    }

    private fun listenToGenderButton() {
        binding.selectGender.setOnClickListener {
            val gender = resources.getStringArray(R.array.gender)
            createOptionsPickerDialog(gender, true)
        }
    }

    private fun listenToStatusButton() {
        binding.selectStatus.setOnClickListener {
            val status = resources.getStringArray(R.array.status)
            createOptionsPickerDialog(status, false)
        }
    }

    private fun setupInputFieldListeners() {
        val nameInput = binding.itemName.value
        val emailInput = binding.itemEmail.value
        nameInput.addTextChangedListener {
            viewModel.onEvent(
                CreateClientEvent.NameInput(
                    it?.toString().orEmpty()
                )
            )
        }
        emailInput.addTextChangedListener {
            viewModel.onEvent(
                CreateClientEvent.EmailInput(
                    it?.toString().orEmpty()
                )
            )
        }
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(state: CreateClientState) {
        val client = state.client
        binding.btnSave.isEnabled = state.isFormValid
        binding.progressBar.isVisible = state.isSubmitting
        binding.selectStatus.text = if(client.status.isEmpty()) getString(R.string.select) else client.status
        binding.selectGender.text = if(client.gender.isEmpty()) getString(R.string.select) else client.gender
        handleSuccess(state.success)
        handleFailures(state.failure)
    }



    private fun createOptionsPickerDialog(options: Array<String>, isGender: Boolean) {
        val title =
            if (isGender) resources.getString(R.string.select_gender) else resources.getString(R.string.select_status)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setItems(options) { _, which ->
                val selection = options[which]
                val event = if (isGender) CreateClientEvent.GenderSelected(selection)
                else CreateClientEvent.StatusSelected(selection)
                viewModel.onEvent(event)
            }
            .show()
    }

    private fun handleSuccess(success: Event<Boolean>?) {
        val isSuccess = success?.getContentIfNotHandled() ?: return
        if(isSuccess){
            val message = getString(R.string.user_created_successfully)
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
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