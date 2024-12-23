package com.vandan.tripsheep.data.resource

data class SignUpState (
    var signUpSuccess: Boolean = false,
    var loginError: String? = null,
    val isLoading: Boolean = false
)