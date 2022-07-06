package com.proyecto.reto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.proyecto.reto.databinding.ActivityMainBinding
import com.proyecto.reto.utils.hideKeyboard
import com.proyecto.reto.viemodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var mViewModel : MainViewModel? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = binding.etEmail
        password = binding.etPassword
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
        subscribeObservers()
    }

    private fun subscribeObservers(){
        mViewModel?.email?.observe(this) { _ ->
            mViewModel?.password?.observe(this) { _ ->
                mViewModel?.enabledLogin()
            }
        }

        mViewModel?.correcto?.observe(this) { correcto ->
            mViewModel?.token?.observe(this) { token ->
                if (correcto) {
                    if (token != "") {
                        mViewModel?.correctoFalse()
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    }
                }
            }
        }

        mViewModel?.incorrecto?.observe(this) { incorrecto ->
            if (incorrecto) {
                val errorMessage = mViewModel?.errorMessage?.value.toString()
                if (errorMessage != "") {
                    mViewModel?.incorrectoFalse()
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()

                }

            }
        }

        mViewModel?.ocultarTeclado?.observe(this) { ocultarTeclado ->
            if (ocultarTeclado) {
                applicationContext?.hideKeyboard(email)
                applicationContext?.hideKeyboard(password)
                mViewModel?.ocultarTecladoFalse()
            }
        }
    }
}