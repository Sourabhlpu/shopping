package com.example.shopping.common.data.api.model.mappers

import com.example.shopping.common.data.api.model.ApiPagination
import com.example.shopping.common.domain.model.pagination.Pagination
import javax.inject.Inject

class ApiPaginationMapper @Inject constructor() : ApiMapper<ApiPagination?, Pagination> {
    override fun mapToDomain(apiEntity: ApiPagination?): Pagination {
        return Pagination(
            currentPage = apiEntity?.currentPage ?: 0,
            totalPages = apiEntity?.totalPages ?: 0
        )
    }

    override fun mapFromDomain(domainEntity: Pagination): ApiPagination? {
        return ApiPagination()
    }
}