package com.android.segunfrancis.gdgph.model

import java.io.Serializable

data class Feedback(
    var refreshmentRating: Float, var venueRating: Float, var timeRating: Float,
    var overallRating: Float, var comments: String
): Serializable {
    constructor() : this(-1f, -1f, -1f, -1f, "")
}