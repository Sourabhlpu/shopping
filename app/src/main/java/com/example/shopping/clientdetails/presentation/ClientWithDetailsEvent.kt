package com.example.shopping.clientdetails.presentation

sealed class ClientWithDetailsEvent {
    object RequestInitialClientList: ClientWithDetailsEvent()
}