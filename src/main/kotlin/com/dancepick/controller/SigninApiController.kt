package com.dancepick.controller

import com.dancepick.common.ApiResponse
import com.dancepick.domain.auth.SigninRequest
import com.dancepick.domain.auth.SigninService
import com.dancepick.domain.auth.SignsearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class SigninApiController @Autowired constructor(
    private val signinService: SigninService
) {

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest) = ApiResponse.ok(signinService.signin(signinRequest))

}