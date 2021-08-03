package com.example.shopping.displayclients.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping.common.presentation.ClientsAdapter
import com.example.shopping.databinding.FragmentClientsBinding

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
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
    }

    private fun setupRecyclerView(clientsAdapter: ClientsAdapter) {
        binding.clientsRecyclerView.apply {
            adapter = clientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun createAdapter(): ClientsAdapter {
        return ClientsAdapter()
    }

}