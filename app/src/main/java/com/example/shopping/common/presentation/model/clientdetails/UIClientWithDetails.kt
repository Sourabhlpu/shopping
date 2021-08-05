package com.example.shopping.common.presentation.model.clientdetails

data class UIClientWithDetails(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
) {
    constructor() : this(
        -1,
        "",
        "",
        "",
        ""
    )

    val isEmpty
        get() = name.isEmpty() || email.isEmpty() || gender.isEmpty() || status.isEmpty()

}