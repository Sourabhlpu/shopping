package com.example.shopping.displayclients.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.NetworkUnavailableException
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.mappers.UiClientMapper
import com.example.shopping.common.utils.DispatchersProvider
import com.example.shopping.common.utils.createExceptionHandler
import com.example.shoppingapp.Logger
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClientsFragmentViewModel constructor(
    private val uiAnimalMapper: UiClientMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
    val state: LiveData<ClientsViewState> get() = _state
    private val _state = MutableLiveData<ClientsViewState>()
    private var currentPage = 0
    init {
        _state.value = ClientsViewState()
    }

    fun onEvent(event: ClientsEvent) {
        when(event) {
            is ClientsEvent.RequestInitialClientList -> loadAnimals()
        }
    }

    private fun loadAnimals() {
        if (state.value!!.clients.isEmpty()) { // 2
            loadNextAnimalPage()
        }
    }
    private fun loadNextAnimalPage() {
        val errorMessage = "Failed to fetch nearby animals"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
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
        }
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}