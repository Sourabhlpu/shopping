package com.example.shopping.displayclients.presentation

sealed class ClientsEvent {
    object RequestInitialClientList: ClientsEvent()
    object RequestMoreClients: ClientsEvent()
}