package com.cognizant.app.skills.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill(
    var skillId: Int,
    var skillName: String,
    var years: Int,
    var levelType: Int,
    var levelName: String
): Parcelable
