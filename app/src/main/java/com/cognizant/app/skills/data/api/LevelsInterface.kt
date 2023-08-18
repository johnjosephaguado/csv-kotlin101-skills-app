package com.cognizant.app.skills.data.api

import com.cognizant.app.skills.data.LevelsResponse
import retrofit2.Response
import retrofit2.http.GET

interface LevelsInterface {
    @GET("/api/getLevels")
    suspend fun getLevels(): Response<List<LevelsResponse>>
}