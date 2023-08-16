package com.cognizant.app.skills.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.64.1:7171")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
