package com.example.shopping.createclient.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping.common.domain.CreateUserException
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.NetworkUnavailableException
import com.example.shopping.common.presentation.Event
import com.example.shopping.common.presentation.model.mappers.UiClientDetailsMapper
import com.example.shopping.common.utils.DispatchersProvider
import com.example.shopping.common.utils.createExceptionHandler
import com.example.shopping.createclient.domain.usecases.SaveClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateClientViewModel @Inject constructor(
    private val uiMapper: UiClientDetailsMapper,
    private val saveClient: SaveClient,
    private val dispatcherProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    val state: LiveData<CreateClientState> get() = _state
    private val _state = MutableLiveData<CreateClientState>()

    init {
        _state.value = CreateClientState()
    }

    fun onEvent(event: CreateClientEvent) {
        when (event) {
            is CreateClientEvent.SaveClient -> saveClient()
            is CreateClientEvent.EmailInput -> validateEmail(event.email)
            is CreateClientEvent.NameInput -> validateName(event.name)
            is CreateClientEvent.GenderSelected -> validateGender(event.gender)
            is CreateClientEvent.StatusSelected -> validateStatus(event.status)
        }
    }

    private fun validateStatus(status: String) {
        val client = state.value!!.client.copy(status = status)
        _state.value = state.value!!.copy(
            client = client,
            isFormValid = !client.isEmpty
        )
    }

    private fun validateGender(gender: String) {
        val client = state.value!!.client.copy(gender = gender)
        _state.value = state.value!!.copy(
            client = client,
            isFormValid = !client.isEmpty
        )
    }

    private fun validateEmail(email: String) {
        val client = state.value!!.client.copy(email = email)
        _state.value = state.value!!.copy(
            client = client,
            isFormValid = !client.isEmpty
        )
    }

    private fun validateName(name: String) {
        val client = state.value!!.client.copy(name = name)
        _state.value = state.value!!.copy(
            client = client,
            isFormValid = !client.isEmpty
        )

    }


    private fun saveClient() {
        _state.value = state.value!!.copy(isSubmitting = true)
        val errorMessage = "Failed to save"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
            onFailure(it)
        }
        viewModelScope.launch(exceptionHandler) {
         val isSuccess = saveClient(uiMapper.mapToDomain(state.value!!.client))
            _state.value = state.value!!.copy(
                isSubmitting = false,
                success = Event(isSuccess)
            )
        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    isSubmitting = false,
                    failure = Event(failure)
                )
            }
            is  CreateUserException-> {
                _state.value = state.value!!.copy(
                    isSubmitting = false,
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