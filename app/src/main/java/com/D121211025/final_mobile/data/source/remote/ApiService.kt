package com.D121211025.final_mobile.data.source.remote

import com.D121211025.final_mobile.data.response.GetMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NDU0ZjZkNjM5MzQ4NjgyYjAwMjAyZGM5NGNiYjBmNSIsInN1YiI6IjY1M2RmMmIyNTA3MzNjMDEyMTliMGM4OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GmhmsTJNubRDq0VYPUcoESJO6OIgdXzIfB6B1xIf-hQ"
    )
    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): GetMovieResponse
}