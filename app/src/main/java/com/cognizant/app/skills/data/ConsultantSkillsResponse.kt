package com.cognizant.app.skills.data

import com.google.gson.annotations.SerializedName

data class ConsultantSkillsResponse(
    @SerializedName("skillId")
    var skillId: Int,
    @SerializedName("skillName")
    var skillName: String,
    @SerializedName("years")
    var years: Int,
    @SerializedName("levelType")
    var levelType: Int,
    @SerializedName("levelName")
    var levelName: String,
)
