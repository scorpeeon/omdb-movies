package com.scrpn.omdb.omdbmovies.ui.list;

import com.scrpn.omdb.omdbmovies.network.model.Movie;

import java.util.List;

interface MovieListScreen {
    void showLoading(boolean loading);

    void onMoviesLoaded(List<Movie> movies);

    void onLoadFailed();
}
