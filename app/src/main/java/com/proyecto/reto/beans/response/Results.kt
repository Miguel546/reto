package com.proyecto.reto.beans.response

data class Results(
    val refreshToken: String,
    val token: String,
    val user: User
)