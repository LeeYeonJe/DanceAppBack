package com.dancepick.controller

import com.dancepick.common.ApiResponse
import com.dancepick.domain.auth.SignsearchEmailRequest
import com.dancepick.domain.auth.SignsearchPasswordRequest
import com.dancepick.domain.auth.SignsearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class SignsearchApiController @Autowired constructor(
    private val signsearchService: SignsearchService
){

    @GetMapping("/signsearch/email")
    fun signsearchemail(@RequestBody signsearchEmailRequest: SignsearchEmailRequest)
    = ApiResponse.ok(signsearchService.signSearchEmail(signsearchEmailRequest))

    @GetMapping("/signsearch/password")
    fun signsearchpassword(@RequestBody signsearchPasswordRequest: SignsearchPasswordRequest)
    = ApiResponse.ok(signsearchService.signSearchPassword(signsearchPasswordRequest))
}