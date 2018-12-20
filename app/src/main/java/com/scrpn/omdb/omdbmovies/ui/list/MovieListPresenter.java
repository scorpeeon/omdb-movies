package com.scrpn.omdb.omdbmovies.ui.list;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.RxPresenter;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    @Inject
    OmdbApi apiService;

    @Inject
    public MovieListPresenter() {

    }

    public void refreshItems(String searchString) {

        if (screen != null) {
            screen.showLoading(true);
        }
        attachDisposable(
                apiService.getMovies("movie", searchString, BuildConfig.OMDB_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (screen != null) {
                        screen.showLoading(false);
                        if (result.getSearch() != null) {
                            screen.onMoviesLoaded(result.getSearch());
                        }
                    }
                }, error -> {
                    if (screen != null) {
                        screen.onLoadFailed();
                    }
                }));
    }
}
