package com.scrpn.omdb.omdbmovies;

public abstract class Presenter<S> {

    protected S screen;

    public void attachScreen(S screen) {
        this.screen = screen;
    }

    public void detachScreen() {
        this.screen = null;
    }

}
