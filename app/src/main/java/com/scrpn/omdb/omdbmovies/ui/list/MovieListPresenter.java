package com.scrpn.omdb.omdbmovies.ui.list;

import com.scrpn.omdb.omdbmovies.BuildConfig;
import com.scrpn.omdb.omdbmovies.RxPresenter;
import com.scrpn.omdb.omdbmovies.interactor.NetworkInteractor;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends RxPresenter<MovieListScreen> {
    @Inject
    NetworkInteractor networkInteractor;

    @Inject
    public MovieListPresenter() {

    }

    public void refreshItems(String searchString) {

        if (screen != null) {
            screen.showLoading(true);
        }
        attachDisposable(
                networkInteractor.getMovies(searchString)
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
                        screen.showLoading(false);
                        screen.onLoadFailed();
                    }
                }));
    }
}
