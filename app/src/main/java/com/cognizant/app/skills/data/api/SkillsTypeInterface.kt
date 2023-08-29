package com.cognizant.app.skills.data.api

import com.cognizant.app.skills.data.SkillsTypeResponse
import retrofit2.Response
import retrofit2.http.GET

interface SkillsTypeInterface {
    @GET("/api/getSkillTypes")
    suspend fun getSkillTypes(): Response<List<SkillsTypeResponse>>
}