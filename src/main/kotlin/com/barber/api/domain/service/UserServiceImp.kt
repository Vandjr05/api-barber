package com.barber.api.domain.service

import com.barber.api.domain.dtos.request.LoginRequestDto
import com.barber.api.domain.dtos.request.UserRequestDto
import com.barber.api.domain.dtos.response.UserResponseDto
import com.barber.api.domain.entities.User
import com.barber.api.infrastructure.repository.UserRepository
import com.barber.api.util.CodeGeneratorUtil
import org.springframework.stereotype.Service
import java.nio.file.attribute.UserPrincipalNotFoundException

@Service
class UserServiceImp (
    private val userRepository: UserRepository,
    private val codeGeneratorUtil: CodeGeneratorUtil,
):UserServiceInterface {
    override fun save(userRequestDto: UserRequestDto): UserResponseDto {
        val existingUser = userRepository.findByCpf(userRequestDto.cpf)

        if (existingUser != null) {
            throw IllegalArgumentException("Já existe um usuário com o CPF ${userRequestDto.cpf}")
        }
        val user = userRepository.save(
            User(
                firstName = userRequestDto.firstName,
                lastName = userRequestDto.lastName,
                email = userRequestDto.email,
                password = userRequestDto.password,
                cpf = userRequestDto.cpf,
                barber = userRequestDto.barber,
                code = codeGeneratorUtil.gerarProximoCodigo()
            )
        )
        return UserResponseDto(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = user.password,
            cpf = user.cpf,
            barber = user.barber,
        )
    }

    override fun update(id:Long, userRequestDto: UserRequestDto): UserResponseDto {
        val user = userRepository.findById(id).orElseThrow{
            IllegalArgumentException("Erro");
        }


        user.firstName = userRequestDto.firstName
        user.lastName = userRequestDto.lastName
        user.email = userRequestDto.email
        user.password = userRequestDto.password
        user.cpf = userRequestDto.cpf

        val userUpdate = userRepository.save(user)
        return UserResponseDto(
            id = userUpdate.id,
            firstName = userUpdate.firstName,
            lastName = userUpdate.lastName,
            email = userUpdate.email,
            password = userUpdate.password,
            cpf = userUpdate.cpf,
            barber = userUpdate.barber,
        )
    }

    override fun delete(id: Long) {
        val deleteUser = userRepository.findById(id).orElseThrow {
            UserPrincipalNotFoundException("Usuário com ID $id não encontrado.")
        }
        userRepository.delete(deleteUser)
    }

    override fun authLoginClient(loginRequestDto: LoginRequestDto): Boolean {
        val user: User? = userRepository.findByCpf(loginRequestDto.cpfCnpj)

        return user != null && loginRequestDto.password == user.password
    }

    override fun getUserDataByCpf(cpf: String): User? {
        return userRepository.findByCpf(cpf)
    }
}