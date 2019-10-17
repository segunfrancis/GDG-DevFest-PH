package com.android.segunfrancis.gdgph.model

data class Activities(
    var id: String, var speakerName: String, var topic: String, var time: String,
    var timeConversation: String, var feedback: String, var description: String,
    var difficulty: String, var track: String, var photoUrl: String
) {
    constructor() : this("", "", "", "", "", "",
        "", "", "", "")
}