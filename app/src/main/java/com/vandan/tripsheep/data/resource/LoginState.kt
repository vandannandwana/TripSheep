package com.vandan.tripsheep.data.resource

data class LoginState(
    var loginSuccess: Boolean = false,
    var loginError: String? = null,
    val isLoading: Boolean = false
)