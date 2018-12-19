package com.scrpn.omdb.omdbmovies.ui.details;

import android.support.annotation.NonNull;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.Presenter;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        Observable<DetailedMovie> movie = apiService.getMovie(imdbId, BuildConfig.OMDB_API_KEY);
        movie
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (screen != null) {
                        screen.showLoading(false);
                        screen.onMovieLoaded(result);
                    }
                });

        // TODO call screen.onLoadFailed();
    }
}
