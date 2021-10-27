package com.dancepick.domain.auth

import com.dancepick.common.DancepickException
import com.dancepick.domain.user.User
import com.dancepick.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SigninService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun signin(signinRequest: SigninRequest):SigninResponse{
        val user = userRepository
            .findByEmail(signinRequest.email.toLowerCase())
            ?: throw DancepickException("로그인 정보(이메일)에 문제가 있습니다. - DB에서 이메일정보 검색안됨")

        if (isNotValidPassword(signinRequest.password,user.password)){
            throw DancepickException("로그인 정보(비밀번호)에 문제가 있습니다. - DB에 저장된 pw가 현재 request의 pw와 다름")
        }
    return responseWithTokens(user)
    }

    private fun isNotValidPassword(
        plain: String,
        hashed: String,
    ) = BCrypt.checkpw(plain,hashed)
        .not()

    private fun responseWithTokens(user: User)= user.no?.let{userNo ->
        SigninResponse(
            JWTUtil.createToken(user.email),
            JWTUtil.createRefreshToken(user.email),
            user.name,
            userNo
        )
    } ?: throw IllegalStateException("user.no 없음.")
}