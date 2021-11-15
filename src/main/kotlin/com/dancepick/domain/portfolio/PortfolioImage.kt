package com.dancepick.domain.portfolio

import com.dancepick.domain.jpa.BaseEntity
import javax.persistence.Entity


@Entity(name = "user_portphoto")
class PortfolioImage(
    var portPhotoPath : String,
    var portPhotoText : String,
    var portPhotoId : Long? = null
) :BaseEntity(){

}