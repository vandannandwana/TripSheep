package com.vandan.tripsheep.data.resource

data class DataState<T>(
    val data: T? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)