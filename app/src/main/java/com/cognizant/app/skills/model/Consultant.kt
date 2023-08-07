package com.cognizant.app.skills.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Consultant(
    var avatar: Int,
    var firstName: String,
    var lastName: String,
    var position: String
): Parcelable