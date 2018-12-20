package com.scrpn.omdb.omdbmovies.interactor;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.OmdbMovieApplication;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkInteractor {

    public NetworkInteractor() {
        OmdbMovieApplication.injector.inject(this);
    }

    @Inject
    OmdbApi apiService;

    public Observable<SearchResponse> getMovies(String searchString) {
        return apiService.getMovies("movie", searchString, BuildConfig.OMDB_API_KEY);
    }

    public Observable<DetailedMovie> getMovie(String imdbId) {
        return apiService.getMovie(imdbId, BuildConfig.OMDB_API_KEY);
    }
}
