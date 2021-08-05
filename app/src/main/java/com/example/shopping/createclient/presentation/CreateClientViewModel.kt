package com.example.shopping.createclient.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class CreateClientViewModel @Inject constructor(
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
        }
    }

    private fun validateEmail(email: String) {
        _state.value = state.value!!.copy(
            email = email
        )
        validateForm()
    }

    private fun validateName(name: String) {
        _state.value = state.value!!.copy(
            name = name,
        )
        validateForm()
    }



    private fun validateForm(){
        _state.value = state.value!!.copy(
            isFormValid = state.value!!.validateForm()
        )
    }

    private fun saveClient() {

    }
}