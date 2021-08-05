package com.example.shopping.common.domain.model.client

import com.example.shopping.common.domain.model.client.details.Gender
import com.example.shopping.common.domain.model.client.details.Status

data class Client(
    val id : Long,
    val name: String,
    val email : String,
    val gender: Gender,
    val status: Status
){
    constructor() : this(
        -1,
        "",
        "",
        Gender.UNKNOWN,
        Status.ALL
    )
}