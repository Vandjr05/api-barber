package com.barber.api.domain.dtos.response

data class UserResponseDto(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val password: String,
    val cpf: String,
    val barber: Boolean
)
