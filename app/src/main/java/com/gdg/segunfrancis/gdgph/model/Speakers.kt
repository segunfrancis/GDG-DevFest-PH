package com.gdg.segunfrancis.gdgph.model

import java.io.Serializable

data class Speakers(
    var fullName: String,
    var tagLine: String,
    var bio: String,
    var profilePicture: String,
    var sessions: List<String>
) : Serializable {
    constructor() : this("", "", "", "", listOf(""))
}