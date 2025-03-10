package com.barber.api.domain.entities

import jakarta.persistence.*

@Table(name = "users")
@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    var id:Long? = null,

    @Column(name = "first_name")
    var firstName:String? = null,

    @Column(name = "last_name")
    var lastName:String? = null,

    @Column(name = "email")
    var email:String? = null,

    @Column(name = "password")
    var password: String,

    @Column(name = "cpf")
    var cpf: String,

    @Column(name = "barber")
    var barber: Boolean,

    @Column(name = "code")
    var code: String

    )