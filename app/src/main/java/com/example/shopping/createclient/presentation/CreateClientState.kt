package com.example.shopping.createclient.presentation

import com.example.shopping.common.presentation.Event

data class CreateClientState(
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val status: String = "",
    val isFormValid: Boolean = false,
    val isSubmitting: Boolean = false,
    val failure: Event<Throwable>? = null
) {
    fun validateForm() =
        name.isNotEmpty() && email.isNotEmpty() && gender.isNotEmpty() && status.isNotEmpty()

}