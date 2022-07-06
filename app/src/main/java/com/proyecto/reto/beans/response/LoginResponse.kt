package com.proyecto.reto.beans.response

data class LoginResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)