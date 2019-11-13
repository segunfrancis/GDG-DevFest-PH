package com.gdg.segunfrancis.gdgph.model

import java.io.Serializable

data class SpeakerFeedback(
    var clarity: Float, var knowledgeOfTopic: Float, var overall: Float, var comments: String
) : Serializable {
    constructor() : this(-1f, -1f, -1f, "")
}