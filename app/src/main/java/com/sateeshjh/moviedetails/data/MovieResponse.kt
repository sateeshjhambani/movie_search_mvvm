package com.sateeshjh.moviedetails.data

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)