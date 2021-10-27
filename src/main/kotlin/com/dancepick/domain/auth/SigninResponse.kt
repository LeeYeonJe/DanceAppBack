package com.dancepick.domain.auth

data class SigninResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val userNo: Long
)
