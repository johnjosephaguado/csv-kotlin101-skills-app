package com.cognizant.app.skills.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://<replace-here>:7171")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
