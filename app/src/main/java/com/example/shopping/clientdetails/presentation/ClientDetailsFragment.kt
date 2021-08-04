package com.example.shopping.clientdetails.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping.R
import com.example.shopping.common.presentation.ClientsAdapter
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.UIToolbar
import com.example.shopping.databinding.FragmentClientDetailsBinding
import com.example.shopping.databinding.FragmentClientsBinding
import com.example.shopping.displayclients.presentation.ClientsEvent
import com.example.shopping.displayclients.presentation.ClientsFragmentViewModel
import com.example.shopping.displayclients.presentation.ClientsViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


const val ARG_CLIENT = "client"

@AndroidEntryPoint
class ClientDetailsFragment : Fragment() {
    private var clientId: Long? = null

    private val binding get() = _binding!!

    private var _binding: FragmentClientDetailsBinding? = null

    private val viewModel: ClientDetailsViewModel by viewModels()

    companion object {
        val uiToolbar : UIToolbar
            get() = UIToolbar(showSpinner = false, showTitle = true, showRightAction = false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clientId = it.getLong(ARG_CLIENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_client_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clientId = clientId ?: 0
        setupUI()
        requestInitialClientList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun requestInitialClientList() {
        viewModel.onEvent(ClientWithDetailsEvent.RequestInitialTodos)
    }

    private fun observeViewStateUpdates(adapter: ClientsAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(
        state: ClientWithDetailsState,
        adapter: ClientsAdapter
    ) {
        binding.progressBar.isVisible = state.loading
        //adapter.submitList(state.clientWithTodos.to)
        binding.client = state.clientWithTodos
        handleFailures(state.failure)
    }
    private fun createAdapter(): ClientsAdapter {
        return ClientsAdapter {
            findNavController().navigate(R.id.action_client_to_detail)
        }
    }

    private fun setupRecyclerView(clientsAdapter: ClientsAdapter) {
        binding.rvTodos.apply {
            adapter = clientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
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