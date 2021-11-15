package com.dancepick.domain.user

import com.dancepick.domain.jpa.BaseEntity
import com.dancepick.domain.portfolio.Portfolio
import com.dancepick.domain.portfolio.PortfolioImage
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
    var recentAt: Date, //유저의 최근 접속일
    @OneToOne
    @JoinColumn(name = "no")
    var portfolios: MutableList<Portfolio>?



        ) :BaseEntity()

