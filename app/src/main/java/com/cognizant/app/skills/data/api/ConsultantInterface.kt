package com.cognizant.app.skills.data.api

import com.cognizant.app.skills.data.ConsultantResponse
import com.cognizant.app.skills.data.ConsultantSkillsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsultantInterface {

    @GET("/api/consultants")
    suspend fun getConsultants(): Response<List<ConsultantResponse>>

    @GET("/api/consultant/search/skillname/{skill}")
    suspend fun searchConsultantsBySkill(@Path("skill") skill: String): Response<List<ConsultantResponse>>

    @GET("/api/consultant/{id}")
    suspend fun getConsultById(@Path("id") id: Int): Response<ConsultantResponse>

    @GET("/api/consultant/{id}/skills")
    suspend fun getConsultSkillsById(@Path("id") id: Int): Response<List<ConsultantSkillsResponse>>
}
