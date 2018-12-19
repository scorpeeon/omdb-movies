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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
        Observable<SearchResponse> movies = apiService.getMovies("movie", searchString, BuildConfig.OMDB_API_KEY);
        movies
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (screen != null) {
                        screen.showLoading(false);
                        if (result.getSearch() != null) {
                            screen.onMoviesLoaded(result.getSearch());
                        }
                    }
                });

        // TODO call screen.onLoadFailed();
    }
}
