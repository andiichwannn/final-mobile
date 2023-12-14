package com.D121211025.final_mobile.data.repository

import com.D121211025.final_mobile.data.response.GetMovieResponse
import com.D121211025.final_mobile.data.source.remote.ApiService

class MovieRepository(private val apiService: ApiService) {

    suspend fun getMovies(): GetMovieResponse {
        return apiService.getMovies()
    }
}