package com.scrpn.omdb.omdbmovies;

import android.app.Application;

public class OmdbMovieApplication extends Application {
    public static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        setupInjector();
        injectDependencies(injector);
    }

    protected void setupInjector() {
        injector = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    protected void injectDependencies(final AppComponent appComponent) {
        appComponent.inject(this);
    }
}
