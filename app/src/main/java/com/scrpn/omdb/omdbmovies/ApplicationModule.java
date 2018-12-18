package com.scrpn.omdb.omdbmovies;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    @Singleton
    @Provides
    static Context provideApplicationContext(final Application application) {
        return application.getApplicationContext();
    }
}
