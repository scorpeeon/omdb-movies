package com.scrpn.omdb.omdbmovies.network.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
        @SerializedName("Search")
        val search: List<Movie>,
        val totalResults: String,
        @SerializedName("Response")
        val response: String
)