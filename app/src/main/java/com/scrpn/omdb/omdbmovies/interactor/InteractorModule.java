package com.scrpn.omdb.omdbmovies.interactor;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    public NetworkInteractor provideNetworkInteractor() {
        return new NetworkInteractor();
    }
}
