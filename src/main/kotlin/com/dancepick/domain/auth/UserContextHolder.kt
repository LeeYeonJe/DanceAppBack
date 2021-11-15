package com.dancepick.domain.auth

import com.dancepick.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserContextHolder @Autowired constructor(
    private val userRepository: UserRepository
){

    private val userHolder = ThreadLocal.withInitial {
        UserHolder()
    }


    val no:Long? get() = userHolder.get().no
    val email: String? get() = userHolder.get().email
    val name: String? get() = userHolder.get().name
    val userClass: Boolean? get() = userHolder.get().userClass

    fun set(email: String) = userRepository
        .findByEmail(email)?.let {
            user -> this.userHolder.get().apply {
                this.no = user.no
                this.email = user.email
                this.name = user.name
                this.userClass = user.userClass
        }.run(userHolder::set)
        }

    fun clear(){
        userHolder.remove()
    }

    class UserHolder{
        var no: Long? = null
        var email: String? = null
        var name: String? = null
        var userClass : Boolean? = null
    }
}