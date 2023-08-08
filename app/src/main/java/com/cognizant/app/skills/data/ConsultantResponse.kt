package com.cognizant.app.skills.data

import com.google.gson.annotations.SerializedName

data class ConsultantResponse(
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("designation")
    var designation: String
)
