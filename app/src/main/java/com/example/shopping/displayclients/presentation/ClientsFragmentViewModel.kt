package com.example.shopping.displayclients.presentation

import androidx.lifecycle.ViewModel
import com.example.shopping.common.presentation.model.mappers.UiClientMapper
import com.example.shopping.common.utils.DispatchersProvider
import io.reactivex.disposables.CompositeDisposable

class ClientsFragmentViewModel constructor(
    private val uiAnimalMapper: UiClientMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}