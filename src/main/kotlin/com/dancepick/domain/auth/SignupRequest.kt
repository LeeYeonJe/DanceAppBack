package com.dancepick.domain.auth

import java.util.*

data class SignupRequest(
    var email: String,
    var password: String,
    var name: String,
    var phoneNum: String,
    var birth: Date,
    var userClass: Boolean,
    var nickName: String,
    var recentAt: Date
)
