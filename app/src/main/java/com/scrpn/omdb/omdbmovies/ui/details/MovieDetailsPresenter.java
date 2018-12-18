package com.scrpn.omdb.omdbmovies.ui.details;

import android.support.annotation.NonNull;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.Presenter;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenter extends Presenter<MovieDetailsScreen> {
    @Inject
    OmdbApi apiService;

    @Inject
    public MovieDetailsPresenter() {

    }

    public void getMovieDetails(String imdbId) {

        if (screen != null) {
            screen.showLoading(true);
        }
        Call<DetailedMovie> call = apiService.getMovie(imdbId, BuildConfig.OMDB_API_KEY);
        call.enqueue(new Callback<DetailedMovie>() {
            @Override
            public void onResponse(@NonNull Call<DetailedMovie> call, @NonNull Response<DetailedMovie> response) {
                if (screen != null) {
                    screen.showLoading(false);
                }
                int statusCode = response.code();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    DetailedMovie movie = response.body();

                    if (movie != null && screen != null) {
                        screen.onMovieLoaded(movie);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailedMovie> call, @NonNull Throwable t) {
                if (screen != null) {
                    screen.onLoadFailed();
                }
            }
        });
    }
}
