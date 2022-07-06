package com.proyecto.reto.beans.response

data class User(
    val campus: String,
    val documentNumber: String,
    val edifice: String,
    val email: String,
    val fullName: String,
    val id: String,
    val isActivate: Boolean,
    val isPassTemp: Boolean,
    val isResponsible: Boolean,
    val phoneNumber: String,
    val turn: String,
    val typeDocument: String,
    val typeUser: String
)