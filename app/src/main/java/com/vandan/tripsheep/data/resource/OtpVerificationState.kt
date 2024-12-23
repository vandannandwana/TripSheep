package com.vandan.tripsheep.data.resource

data class EmailSubmissionState(
    val isLoading: Boolean = false,
    val emailSubmissionSuccess: Boolean = false,
    val emailSubmissionError: String? = null
)

data class OtpVerificationState(
    val isLoading: Boolean = false,
    val otpVerificationSuccess: Boolean = false,
    val otpVerificationError: String? = null
)

data class UserRegisterState(
    val isLoading: Boolean = false,
    var userRegisterSuccess: Boolean = false,
    val userRegisterError: String? = null
)