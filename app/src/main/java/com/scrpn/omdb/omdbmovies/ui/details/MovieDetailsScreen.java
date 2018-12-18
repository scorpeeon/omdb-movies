package com.scrpn.omdb.omdbmovies.ui.details;

import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;

public interface MovieDetailsScreen {
    void onMovieLoaded(DetailedMovie movie);
    void onLoadFailed();
}
