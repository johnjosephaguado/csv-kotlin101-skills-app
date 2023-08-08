package com.cognizant.app.skills.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Consultant(
    var cognizantId: String,
    var avatar: String,
    var firstName: String,
    var lastName: String,
    var designation: String
): Parcelable