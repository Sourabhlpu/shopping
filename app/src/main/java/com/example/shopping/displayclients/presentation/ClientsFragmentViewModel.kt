package com.example.shopping.displayclients.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.NetworkUnavailableException
import com.example.shopping.common.domain.NoMoreClientsException
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.pagination.Pagination
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.mappers.UiClientMapper
import com.example.shopping.common.utils.DispatchersProvider
import com.example.shopping.common.utils.createExceptionHandler
import com.example.shopping.displayclients.domain.usecases.GetClients
import com.example.shopping.displayclients.domain.usecases.RequestNextPageOfClients
import com.example.shoppingapp.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ClientsFragmentViewModel @Inject constructor(
    private val uiClientMapper: UiClientMapper,
    private val requestNextPageOfClients: RequestNextPageOfClients,
    private val getClients: GetClients,
    private val dispatcherProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

    val state: LiveData<ClientsViewState> get() = _state
    var isLoadingMoreClients: Boolean = false
    var isLastPage = false
    private val _state = MutableLiveData<ClientsViewState>()
    private var currentPage = 0

    init {
        _state.value = ClientsViewState()
        subscribeToClientUpdates()
    }

    fun onEvent(event: ClientsEvent) {
        when (event) {
            is ClientsEvent.RequestInitialClientList -> loadClients()
            is ClientsEvent.RequestMoreClients -> loadNextClientsPage()
        }
    }

    private fun subscribeToClientUpdates() {
        getClients()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewClientList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun loadClients() {
        if (state.value!!.clients.isEmpty()) { // 2
            loadNextClientsPage()
        }
    }

    private fun onNewClientList(clients: List<Client>) {
        Logger.d("Got more clients")

        val clients = clients.map { uiClientMapper.mapToView(it) }

        val currentList = state.value!!.clients
        val newClients = clients.subtract(currentList)
        val updateList = currentList + newClients

        _state.value = state.value!!.copy(
            loading = false,
            clients = updateList
        )
    }

    private fun loadNextClientsPage() {
        isLoadingMoreClients = true
        val errorMessage = "Failed to fetch nearby clients"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatcherProvider.io()) {
                Logger.d("Requesting more clients")
                requestNextPageOfClients(++currentPage)
            }
            onPaginationInfoObtained(pagination)
            isLoadingMoreClients = false
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
        isLastPage = !pagination.canLoadMore
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreClientsException -> {
                _state.value = state.value!!.copy(
                    noMoreClients = true,
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}