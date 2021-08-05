package com.example.shopping.createclient.presentation

sealed class CreateClientEvent {
data class NameInput(val name: String) : CreateClientEvent()
    data class EmailInput(val email: String): CreateClientEvent()
    data class GenderSelected(val gender: String): CreateClientEvent()
    data class StatusSelected(val status: String): CreateClientEvent()
    object SaveClient: CreateClientEvent()
}