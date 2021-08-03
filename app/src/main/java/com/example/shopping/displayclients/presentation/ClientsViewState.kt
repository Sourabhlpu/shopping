package com.example.shopping.displayclients.presentation

import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.UIClient

data class ClientsViewState(
    val loading: Boolean = true,
    val clients: List<UIClient> = emptyList(),
    val noMoreClients: Boolean = false,
    val failure: Event<Throwable>? = null
)