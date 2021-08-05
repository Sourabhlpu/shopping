package com.example.shopping.createclient.presentation

import com.example.shopping.common.presentation.Event

data class CreateClientState(
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val status: String = "",
    val failure: Event<Throwable>? = null
) {

}