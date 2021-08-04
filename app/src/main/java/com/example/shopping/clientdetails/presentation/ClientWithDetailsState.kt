package com.example.shopping.clientdetails.presentation

import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.clientdetails.UIClientWithTodos


data class ClientWithDetailsState(
    val loading: Boolean = true,
    val clientWithTodos: UIClientWithTodos = UIClientWithTodos(-1, "", "", emptyList()),
    val noClient: Boolean = false,
    val failure: Event<Throwable>? = null
)