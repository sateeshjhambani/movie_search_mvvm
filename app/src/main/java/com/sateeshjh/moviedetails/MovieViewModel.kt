package com.sateeshjh.moviedetails

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.sateeshjh.moviedetails.data.MovieDetails
import com.sateeshjh.moviedetails.remote.MovieInterface
import com.sateeshjh.moviedetails.ui.movie.MoviePaging
import com.sateeshjh.moviedetails.ui.movieDetails.MovieDetailsRepository
import com.sateeshjh.moviedetails.utils.Events
import com.sateeshjh.moviedetails.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieInterface: MovieInterface, private val repository: MovieDetailsRepository) : ViewModel() {

    private val query = MutableLiveData<String>("")

    val list = query.switchMap { query ->
        Log.d("TAG", "setQuery: " + query)
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    private val _movieDetails =
        MutableLiveData<Events<com.sateeshjh.moviedetails.utils.Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<com.sateeshjh.moviedetails.utils.Result<MovieDetails>>> =
        _movieDetails

    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        _movieDetails.postValue(
            Events(
                com.sateeshjh.moviedetails.utils.Result(
                    Status.LOADING,
                    null,
                    null
                )
            )
        )
        _movieDetails.postValue(
            Events(
                repository.getMovieDetails(imdbId)
            )
        )
    }
}