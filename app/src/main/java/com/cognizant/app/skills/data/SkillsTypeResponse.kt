package com.cognizant.app.skills.data

import com.google.gson.annotations.SerializedName

data class SkillsTypeResponse(
    @SerializedName("typeId")
    var typeId: Int,
    @SerializedName("description")
    var description: String,
)
