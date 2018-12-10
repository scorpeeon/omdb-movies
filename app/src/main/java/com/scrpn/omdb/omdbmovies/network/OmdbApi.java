package com.scrpn.omdb.omdbmovies.network;

import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {
    @GET("/")
    Call<SearchResponse> getMovies(@Query("type") String type, @Query("s") String searchString, @Query("apikey") String apikey);

    @GET("/")
    Call<DetailedMovie> getMovie(@Query("i") String imdbId, @Query("apikey") String apikey);
}
