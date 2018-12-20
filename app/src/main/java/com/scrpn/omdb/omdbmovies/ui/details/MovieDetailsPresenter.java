package com.scrpn.omdb.omdbmovies.ui.details;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.RxPresenter;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsPresenter extends RxPresenter<MovieDetailsScreen> {
    @Inject
    OmdbApi apiService;

    @Inject
    public MovieDetailsPresenter() {

    }

    public void getMovieDetails(String imdbId) {

        if (screen != null) {
            screen.showLoading(true);
        }
        attachDisposable(
                apiService.getMovie(imdbId, BuildConfig.OMDB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (screen != null) {
                        screen.showLoading(false);
                        screen.onMovieLoaded(result);
                    }
                }, error -> {
                    if (screen != null) {
                        screen.onLoadFailed();
                    }
                }));
    }
}
