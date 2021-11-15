package com.dancepick.domain.portfolio

import com.dancepick.domain.jpa.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity(name = "portfolio")
class Portfolio (
    var userStyle : Int,
    var userArea : Int,
    var userAge : Boolean,
    // true - user/birth 참조
    // false - 미표시 / exception - 미성년인 경우 표시
    var userSex : Boolean,
    @Column(length = 5000)
    var userContext : String,
    var userSide : Int,
    var userEdit : Int,
    var userPay : Int,
    var userVideo : String,
    // 유튜브의 경우 자동 썸네일 추출 및
    var userProfileImg : String,
    var userProfileText : String,
    @OneToMany
    @JoinColumn(name = "portPhotoId")
    var portimages: MutableList<PortfolioImage>
        ) : BaseEntity()