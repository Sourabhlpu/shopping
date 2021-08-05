package com.example.shopping.createclient.presentation

import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.clientdetails.UIClientWithDetails
import com.example.shopping.common.presentation.model.clients.UIClient

data class CreateClientState(
    val client: UIClientWithDetails = UIClientWithDetails(),
    val isFormValid: Boolean = !client.isEmpty,
    val isSubmitting: Boolean = false,
    val success: Event<Boolean>? = null,
    val failure: Event<Throwable>? = null
)