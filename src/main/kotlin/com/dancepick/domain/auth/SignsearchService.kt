package com.dancepick.domain.auth

import com.dancepick.common.DancepickException
import com.dancepick.domain.user.User
import com.dancepick.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignsearchService @Autowired constructor(
    private val userRepository: UserRepository
        ){
    fun signSearchEmail(signsearchEmailRequest: SignsearchEmailRequest): SignsearchEmailResponse {

        val user = userRepository.findByPhoneNum(signsearchEmailRequest.phoneNum)
            ?: throw DancepickException("해당하는 전화번호가 없음")

        if (signSearchVerify(signsearchEmailRequest,null,user) == -1) {
            throw DancepickException("입력한 이름과 전화번호가 매칭되지 않습니다")
        } else {

            val targetEmail = user.email.indexOf("@")

            if (targetEmail < 3) {
                throw DancepickException("이메일이 너무 짧아 마스킹처리가 되지 않았습니다. 이메일 형식이 잘못되었습니다.")
                //단순 exception이 아니라 이메일이 짧을 경우 가운데를 마스킹한다거나 예외가 필요하나 적용을 위해서는 정규식 이용이 필수적
            }
            val blockedEmail = user.email
                .replaceRange(3, targetEmail, "*")

            return SignsearchEmailResponse(blockedEmail)
        }
    }


    fun signSearchPassword(signsearchPasswordRequest: SignsearchPasswordRequest): SignsearchPasswordResponse{
        val user = userRepository.findByEmail(signsearchPasswordRequest.email.toLowerCase())
            ?: throw DancepickException("해당하는 이메일이 없음")

        if(signSearchVerify(null,signsearchPasswordRequest, user) == -1){
            throw DancepickException("입력한 이름과 이메일이 매칭되지 않습니다")
        }else{
            return SignsearchPasswordResponse(user.no)
        }
    //null checking - 테이블의 user.no는 jpa가 입력하도록 null로 설정함
        //여기에선 null을 반환해서는 안되는데 reponse에서 nonnullable로 설정해서 문제가 생김
        //$$$$$$$$$$$$$질문이후 변경$$$$$$$$$$$$$$$$$
    }



    private fun signSearchVerify(
        signsearchEmailRequest: SignsearchEmailRequest?,
        signsearchPasswordRequest: SignsearchPasswordRequest?,
        user: User
    ): Int? {

        return signsearchEmailRequest?.let { user.name.indexOf(it.name) }
            ?: signsearchPasswordRequest?.let { user.email.indexOf(it.email) }
    } //왼쪽값이 널이아니면 왼쪽값 반환, logic상으론 null이 나올 수 없어보이나 null check issue 때문에 반환자가 Int?


}