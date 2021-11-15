package com.dancepick.domain.jpa

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var no: Long? = null

    open var createdAt: Date? = null
    open var updatedAt: Date? = null

    @PrePersist
    fun prePersist(){
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun preUpdate(){
        updatedAt = Date()
    }
}