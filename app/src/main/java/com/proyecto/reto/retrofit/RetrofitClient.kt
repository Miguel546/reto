package com.proyecto.reto.retrofit

import com.proyecto.reto.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private var retrofit: Retrofit

    init{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okBuilder = OkHttpClient.Builder()
        okBuilder.readTimeout(10000, TimeUnit.MILLISECONDS)
        okBuilder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        okBuilder.addInterceptor(loggingInterceptor)
        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())

        retrofit = retrofitBuilder.client(okBuilder.build()).build()
    }

    companion object {
        private var INSTANCE: RetrofitClient ? = null

        fun getInstance(): IRetrofit {
            if (INSTANCE == null) {
                INSTANCE = RetrofitClient()
            }
            return INSTANCE?.retrofit?.create(IRetrofit::class.java) as IRetrofit
        }
    }

}