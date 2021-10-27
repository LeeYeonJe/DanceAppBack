package com.dancepick.domain.auth

import com.dancepick.common.DancepickException
import com.dancepick.domain.user.User
import com.dancepick.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class SignupService @Autowired constructor(
    private val userRepository: UserRepository
){
    fun signup(signupRequest: SignupRequest){
        validateRequest(signupRequest)
        checkDuplicates(signupRequest.email,signupRequest.nickName,signupRequest.phoneNum)
        registerUser(signupRequest)
    }

    private fun validateRequest(signupRequest: SignupRequest){
        validateEmail(signupRequest.email)
        validatePassword(signupRequest.password)
        validateName(signupRequest.name)
        validatePhoneNum(signupRequest.phoneNum)
        validateBirth(signupRequest.birth)
        validateNickName(signupRequest.nickName)
    }

    private fun validateEmail(email:String){
        val isNotValidEmail = "^[A-Z0-9._%+-]+@[A-Z0-9._]+\\.[A-Z]{2,6}$"
            .toRegex(RegexOption.IGNORE_CASE)
            .matches(email)
            .not()

        if(isNotValidEmail){
            throw DancepickException("이메일 형식이 올바르지 않음")
        }
    }

    private fun validateName(name:String)
    {
        if(name.trim().length !in 2..10)
            throw DancepickException("이름은 2자 이상 10자 이하여야 합니다.")
    }

    private fun validatePassword(password:String){
        if(password.trim().length !in 8..20)
            throw DancepickException("비밀번호는 공백없이 8자 이상 20자 이하여야 합니다.")
    }

    private fun validatePhoneNum(phoneNum:String){
        val isNotValidPhoneNum = "^[0-9]+$"
            .toRegex()
            .matches(phoneNum)
            .not()

        if(isNotValidPhoneNum || phoneNum.trim().length !in 10..11)
            throw  DancepickException("전화번호는 공백,'-' 기호 없이 10~11자이며 숫자만으로 입력하셔야합니다.")
    }

    private fun validateBirth(birth:Date){
//제대로된 날짜 형식입력인지, 년월일 범위 안인지, 글자수가 정확한지
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR"))//date형태지정
        val stringDate = dateFormat.format(birth)//date를 string으로
        val isNotValidBirth = "^[0-9]{4}+-[0-9]{2}+-[0-9]{2}$"
            .toRegex()
            .matches(stringDate)
            .not()

        if (isNotValidBirth){
            throw DancepickException ("생년월일을 다시 정확하게 입력해주십시오.")
        }
    }

    private fun validateNickName(nickname:String){
        val isNotValidNickName = "^[가-힣A-Z0-9]+$"
            .toRegex(RegexOption.IGNORE_CASE)
            .matches(nickname)
            .not()

        if (isNotValidNickName){
            throw DancepickException("닉네임에는 한글,영어,숫자만 이용가능합니다.")
        }

            if (nickname.trim().length !in 1..10)
            throw DancepickException("닉네임은 1자 이상 10자 이하여야 합니다.")

    }

    private fun checkDuplicates(email: String,nickname: String,phoneNum: String){
        userRepository.findByEmail(email)?.let {
            throw DancepickException("이미 사용중인 이메일입니다.")
        }
        userRepository.findByPhoneNum(phoneNum)?.let {
            throw DancepickException("이미 사용중인 전화번호입니다.")
        }
        userRepository.findByNickName(nickname)?.let {
            throw DancepickException("이미 사용중인 닉네임입니다.")
        }
    }


    private fun registerUser(signupRequest: SignupRequest) =
        with(signupRequest){
            val hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt())
            val user = User(email, hashedPassword, name, phoneNum, birth, userClass, nickName, recentAt)
            userRepository.save(user)
        }

}