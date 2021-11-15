package com.dancepick.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JWTUtil {

    private const val ISSUER = "DPadmin"//토큰발급자
    private const val SUBJECT = "Auth"//토큰제목
    private const val EXPIRE_TIME = 60L * 60 * 2 * 1000 //2시간
    private const val REFRESH_EXPIRE_TIME = 60L * 60 * 24 * 30 * 1000 //30일

    private val SECRET = "DP1234"
    private val algorithm : Algorithm = Algorithm.HMAC256(SECRET)

    private val refreshSecret = "REDP1234"
    private val refreshAlgorithm : Algorithm = Algorithm.HMAC256(refreshSecret)

    fun createToken(email: String) :String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(algorithm)

    fun createRefreshToken(email: String) :String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(refreshAlgorithm)

    fun verify(token: String): DecodedJWT =
        JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun verifyRefresh(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun extractEmail(jwt: DecodedJWT): String =
        jwt.getClaim(JWTClaims.EMAIL).asString()

    object JWTClaims{
        const val EMAIL = "email"
    }
}