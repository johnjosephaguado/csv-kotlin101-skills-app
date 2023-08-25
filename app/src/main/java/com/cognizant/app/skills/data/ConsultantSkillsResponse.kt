package com.cognizant.app.skills.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConsultantSkillsResponse(
    @SerializedName("cognizantId")
    var consultantId: Int = 0,
    @SerializedName("skillId")
    var skillId: Int?,
    @SerializedName("skillTypeId")
    var skillTypeId: Int,
    @SerializedName("skillName")
    var skillName: String?,
    @SerializedName("years")
    var years: Int,
    @SerializedName("levelType")
    var levelType: Int,
    @SerializedName("levelName")
    var levelName: String?,
) : Parcelable
