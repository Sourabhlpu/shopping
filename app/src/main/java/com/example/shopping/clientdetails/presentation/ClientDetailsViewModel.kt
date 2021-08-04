package com.example.shopping.clientdetails.presentation

import androidx.lifecycle.ViewModel
import com.example.shopping.common.presentation.model.mappers.UIClientWithTodosMapper
import com.example.shopping.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    uiMapper: UIClientWithTodosMapper,
    private val dispatcherProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}