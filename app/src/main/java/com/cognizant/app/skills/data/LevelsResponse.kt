package com.cognizant.app.skills.data

import com.google.gson.annotations.SerializedName

data class LevelsResponse(
    @SerializedName("levelId")
    var id: Int,
    @SerializedName("description")
    var description: String,
)
