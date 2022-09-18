package com.sateeshjh.moviedetails.ui.movieDetails

import com.sateeshjh.moviedetails.data.MovieDetails
import com.sateeshjh.moviedetails.remote.MovieInterface
import com.sateeshjh.moviedetails.utils.Constants
import com.sateeshjh.moviedetails.utils.Result
import com.sateeshjh.moviedetails.utils.Status

class MovieDetailsRepository(private val movieInterface: MovieInterface) {

    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> {
        return try {
            val response = movieInterface.getMovieDetails(imdbId, Constants.API_KEY)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body(), null)
            } else {
                Result(Status.ERROR, null, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null, null)
        }
    }
}