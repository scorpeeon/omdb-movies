package com.scrpn.omdb.omdbmovies;

import android.app.Application;

import com.scrpn.omdb.omdbmovies.network.NetworkModule;
import com.scrpn.omdb.omdbmovies.ui.MainActivity;
import com.scrpn.omdb.omdbmovies.ui.details.MovieDetailFragment;
import com.scrpn.omdb.omdbmovies.ui.list.MovieListFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(final OmdbMovieApplication application);

    void inject(MovieListFragment movieListFragment);

    void inject(MovieDetailFragment movieDetailFragment);

    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(final Application application);

        AppComponent build();
    }
}
