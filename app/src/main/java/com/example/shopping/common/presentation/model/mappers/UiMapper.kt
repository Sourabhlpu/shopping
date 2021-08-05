package com.example.shopping.common.presentation.model.mappers

interface UiMapper<E, V> {
    fun mapToView(input: E): V
    fun mapToDomain(input: V): E
}