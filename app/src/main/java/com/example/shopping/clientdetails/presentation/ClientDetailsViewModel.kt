package com.example.shopping.clientdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping.clientdetails.domain.usecases.GetClientWithTodos
import com.example.shopping.clientdetails.domain.usecases.GetTodoForClient
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.NetworkUnavailableException
import com.example.shopping.common.domain.NoMoreClientsException
import com.example.shopping.common.domain.NoMoreTodosException
import com.example.shopping.common.domain.model.client.details.ClientWithTodos
import com.example.shopping.common.domain.model.pagination.Pagination
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.mappers.UIClientWithTodosMapper
import com.example.shopping.common.utils.DispatchersProvider
import com.example.shopping.common.utils.createExceptionHandler

import com.example.shoppingapp.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    private val uiMapper: UIClientWithTodosMapper,
    private val getClientWithTodos: GetClientWithTodos,
    private val getTodosForClient: GetTodoForClient,
    private val dispatcherProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    var clientId: Long = -1
    val state: LiveData<ClientWithDetailsState> get() = _state
    private val _state = MutableLiveData<ClientWithDetailsState>()
    private var currentPage = 0


    init {
        _state.value = ClientWithDetailsState()
        subscribeToClientWithTodosUpdates()
    }

    private fun subscribeToClientWithTodosUpdates() {
        getClientWithTodos(clientId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewClientList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onNewClientList(clientWithTodo: ClientWithTodos) {
        Logger.d("Got the client")
        val clients = uiMapper.mapToView(clientWithTodo)
        _state.value = state.value!!.copy(
            loading = false,
            clientWithTodos = clients
        )
    }

    fun onEvent(event: ClientWithDetailsEvent) {
        when (event) {
            is ClientWithDetailsEvent.RequestInitialTodos -> loadTodos()
        }
    }

    private fun loadTodos() {
        if (state.value!!.clientWithTodos.isEmpty) {
            loadNextClientsPage()
        }
    }

    private fun loadNextClientsPage() {
        val errorMessage = "Failed to fetch todos"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatcherProvider.io()) {
                getTodosForClient(++currentPage, clientId)
            }
        }
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
            is NoMoreTodosException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    noClient = true,
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