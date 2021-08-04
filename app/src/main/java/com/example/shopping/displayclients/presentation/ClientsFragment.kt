package com.example.shopping.displayclients.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R
import com.example.shopping.clientdetails.presentation.ARG_CLIENT
import com.example.shopping.common.presentation.ClientsAdapter
import com.example.shopping.common.presentation.Event
import com.example.shopping.databinding.FragmentClientsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientsFragment : Fragment() {
    private val binding get() = _binding!!

    private var _binding: FragmentClientsBinding? = null

    private val viewModel: ClientsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialClientList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun requestInitialClientList() {
        viewModel.onEvent(ClientsEvent.RequestInitialClientList)
    }

    private fun setupRecyclerView(clientsAdapter: ClientsAdapter) {
        binding.clientsRecyclerView.apply {
            adapter = clientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(createInfiniteScrollListener(layoutManager as LinearLayoutManager))
            setHasFixedSize(true)
        }
    }

    private fun createInfiniteScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            layoutManager,
            ClientsFragmentViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() {
                requestMoreClients()
            }

            override fun isLoading(): Boolean = viewModel.isLoadingMoreClients

            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun requestMoreClients() {
        viewModel.onEvent(ClientsEvent.RequestMoreClients)
    }

    private fun observeViewStateUpdates(adapter: ClientsAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(
        state: ClientsViewState,
        adapter: ClientsAdapter
    ) {
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.clients)
        handleNoMoreClientsNearby(state.noMoreClients)
        handleFailures(state.failure)
    }

    private fun createAdapter(): ClientsAdapter {
        return ClientsAdapter {
            findNavController().navigate(R.id.action_client_to_detail)
        }
    }

    private fun handleNoMoreClientsNearby(noMoreClients: Boolean) {

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

}