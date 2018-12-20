package com.scrpn.omdb.omdbmovies;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class RxPresenter<S> extends Presenter<S> {

    private CompositeDisposable disposables = new CompositeDisposable();

    public RxPresenter() {
    }

    @Override
    public void attachScreen(S screen) {
        super.attachScreen(screen);
        if (disposables != null) {
            disposables.dispose();
        }
        disposables = new CompositeDisposable();
    }


    @Override
    public void detachScreen() {
        if (disposables != null) {
            disposables.dispose();
        }
        super.detachScreen();
    }

    public void attachDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

}
