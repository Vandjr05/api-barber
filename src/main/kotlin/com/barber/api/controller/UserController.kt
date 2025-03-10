package com.barber.api.controller

import com.barber.api.domain.dtos.request.LoginRequestDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.barber.api.domain.dtos.request.UserRequestDto
import com.barber.api.domain.dtos.response.UserResponseDto
import com.barber.api.domain.entities.User
import com.barber.api.domain.service.UserServiceImp
import com.barber.api.domain.service.UserServiceInterface
import com.sun.jdi.request.InvalidRequestStateException
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import java.nio.file.attribute.UserPrincipalNotFoundException

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
class UserController(
    val userService: UserServiceInterface,
    val userServiceImp: UserServiceImp
){
    @PostMapping("/save")
    fun save(@RequestBody userRequestDto: UserRequestDto): ResponseEntity<Any> {
        return try {
            val userResponse = userService.save(userRequestDto)
            ResponseEntity.ok(userResponse)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<String> {

        val isAuthenticated = userServiceImp.authLoginClient(loginRequestDto)

        return if (isAuthenticated) {
            ResponseEntity.ok("Login bem-sucedido!")
        } else {
            ResponseEntity.status(401).body("Credenciais inválidas")
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody userRequestDto: UserRequestDto
    ): UserResponseDto {
        return userService.update(id, userRequestDto)
    }

    @DeleteMapping("/dell/{id}")
    fun delete(@PathVariable id: Long) {
        try {
            userServiceImp.delete(id)
        } catch (e: UserPrincipalNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
        } catch (e: InvalidRequestStateException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Requisição inválida.")
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor. Tente novamente mais tarde.")
        }
    }

    @GetMapping("/data/{cpf}")
    fun getUserData(@PathVariable cpf: String): ResponseEntity<User> {
        return try {
            val userResponseDto = userService.getUserDataByCpf(cpf)
            ResponseEntity.ok(userResponseDto)
        } catch (e: UserPrincipalNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }


}

