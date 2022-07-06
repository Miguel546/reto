package com.proyecto.reto.viemodel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.proyecto.reto.beans.request.LoginRequest
import com.proyecto.reto.beans.response.LoginResponse
import com.proyecto.reto.beans.response.LoginResponseError
import com.proyecto.reto.retrofit.IRetrofit
import com.proyecto.reto.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var iRetrofit: IRetrofit ? = null

    init{
        iRetrofit = RetrofitClient.getInstance()
    }

    private val _email = MutableLiveData("")
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

    private val _incorrecto = MutableLiveData(false)
    val incorrecto: LiveData<Boolean>
        get() = _incorrecto

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _ocultarTeclado = MutableLiveData(false)
    val ocultarTeclado: LiveData<Boolean>
        get() = _ocultarTeclado

    private val _correcto = MutableLiveData(false)
    val correcto: LiveData<Boolean>
        get() = _correcto

    private val _token = MutableLiveData("")
    val token: LiveData<String>
        get() = _token

    val _enabledLogin = MutableLiveData(false)
    var enableLogin: LiveData<Boolean>
        = _enabledLogin

    fun correctoFalse(){
        _correcto.value = false
    }

    fun incorrectoFalse(){
        _incorrecto.value = false
    }

    fun ocultarTecladoFalse(){
        _ocultarTeclado.value = false
    }

    fun updateEmail(s: Editable) {
        _email.value = s.toString()
    }

    fun updatePassword(s: Editable) {
        _password.value = s.toString()
    }

    fun enabledLogin(){
            val email = _email.value.toString()
            val password = _password.value.toString()
            if(email.isBlank() && password.isBlank()){
                _enabledLogin.value = false
            }else if(email.isBlank()){
                _enabledLogin.value = false
            }else if(password.isBlank()){
                _enabledLogin.value = false
            }else{
                _enabledLogin.value = true
            }
    }

    fun onLogin(){
        viewModelScope.launch {
            _progressBar.postValue(true)
            _ocultarTeclado.postValue(true)
            val email = _email.value.toString()
            val password = _password.value.toString()
            val response =
                iRetrofit?.login(LoginRequest(email, password))
            if (response?.isSuccessful == true) {
                _progressBar.postValue(false)
                val body: LoginResponse? = response.body()
                _token.postValue(body?.results?.token)
                _correcto.postValue(true)
            } else {
                _progressBar.postValue(false)
                val gson = Gson()
                val gsonError = response?.errorBody()?.string()
                val errorMaster : LoginResponseError = gson.fromJson(gsonError, LoginResponseError::class.java)
                _errorMessage.postValue(errorMaster.message)
                _incorrecto.postValue(true)
            }

        }
    }
}