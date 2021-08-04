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
import com.example.shopping.common.domain.model.pagination.Pagination
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.mappers.UIClientWithTodosMapper
import com.example.shopping.common.utils.DispatchersProvider
import com.example.shopping.common.utils.createExceptionHandler

import com.example.shoppingapp.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    uiMapper: UIClientWithTodosMapper,
    private val getClientWithTodos: GetClientWithTodos,
    private val getTodosForClient: GetTodoForClient,
    private val dispatcherProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val state: LiveData<ClientWithDetailsState> get() = _state
    private val _state = MutableLiveData<ClientWithDetailsState>()
    private var currentPage = 0

    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

    init {
        _state.value = ClientWithDetailsState()
        //subscribeToClientUpdates()
    }

    fun onEvent(event: ClientWithDetailsEvent) {
        when (event) {
            is ClientWithDetailsEvent.RequestInitialTodos -> loadTodos()
        }
    }

    private fun loadTodos() {
        if(state.value!!.clientWithTodos.isEmpty){

        }
    }

    private fun loadNextClientsPage() {
        val errorMessage = "Failed to fetch todos"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatcherProvider.io()) {
                getTodosForClient( ++currentPage, 72)
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
            is NoMoreClientsException -> {
                _state.value = state.value!!.copy(
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