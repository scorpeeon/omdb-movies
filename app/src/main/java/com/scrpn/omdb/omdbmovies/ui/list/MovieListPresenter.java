package com.scrpn.omdb.omdbmovies.ui.list;

import android.support.annotation.NonNull;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.Presenter;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.Movie;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter extends Presenter<MovieListScreen> {
    @Inject
    OmdbApi apiService;

    @Inject
    public MovieListPresenter() {

    }

    public void refreshItems(String searchString) {

        if (screen != null) {
            screen.showLoading(true);
        }
        Call<SearchResponse> call = apiService.getMovies("movie", searchString, BuildConfig.OMDB_API_KEY);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                if (screen != null) {
                    screen.showLoading(false);
                }
                int statusCode = response.code();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    List<Movie> movies = response.body() != null ? response.body().getSearch() : null;

                    if (movies != null && screen != null) {
                        screen.onMoviesLoaded(movies);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                if (screen != null) {
                    screen.onLoadFailed();
                }
            }
        });
    }
}
