package com.example.shopping.common.di

import com.example.shopping.common.data.ShoppingAppClientsRepository
import com.example.shopping.common.domain.repositories.ClientRepository
import com.example.shopping.common.utils.CoroutineDispatcherProvider
import com.example.shopping.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAnimalsRepository(repository: ShoppingAppClientsRepository) :
            ClientRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatcherProvider):
            DispatchersProvider

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}