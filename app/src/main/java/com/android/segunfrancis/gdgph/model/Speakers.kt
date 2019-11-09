package com.android.segunfrancis.gdgph.model

import java.io.Serializable

data class Speakers(
    var fullName: String,
    var tagLine: String,
    var bio: String,
    var profilePicture: String,
    var sessions: String
) : Serializable {
    constructor() : this("", "", "", "", "")
}