package com.dancepick.common

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class DancepickExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(DancepickException::class)
    fun handleDancepickException(e: DancepickException): ApiResponse{
        logger.error("API error",e)
        return ApiResponse.error(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e:Exception): ApiResponse{
        logger.error("API Error",e)
        return ApiResponse.error("알 수 없는 오류")
    }
}