package com.D121211025.final_mobile.data.response

import com.D121211025.final_mobile.data.models.Result
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMovieResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)