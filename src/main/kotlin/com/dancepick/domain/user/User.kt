package com.dancepick.domain.user

import java.util.*
import javax.persistence.*

@Entity(name = "user")
class User (
    var email: String,
    var password: String,
    var name: String,
    var phoneNum: String,
    var birth: Date,
    var userClass: Boolean,
    var nickName: String,
    var recentAt: Date //유저의 최근 접속일
        )
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var no: Long? = null

    var regAt: Date? = null
    var updateAt: Date? = null

    @PrePersist
    fun prePersist(){
        regAt = Date()
        updateAt = Date()
    }

    @PreUpdate
    fun preUpdate(){//해당 엔티티 업데이트 이전 -> 회원정보 변경시
        updateAt = Date()
    }

}