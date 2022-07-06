package com.proyecto.reto.retrofit

import com.proyecto.reto.beans.request.LoginRequest
import com.proyecto.reto.beans.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IRetrofit {
    @POST("api/auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}