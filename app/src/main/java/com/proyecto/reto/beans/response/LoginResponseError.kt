package com.proyecto.reto.beans.response

data class LoginResponseError(
    val code: Int,
    val error: Boolean,
    val message: String
)