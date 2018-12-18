package com.scrpn.omdb.omdbmovies.network;

import com.scrpn.omdb.omdbmovies.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Singleton
    @Provides
    static Retrofit provideApplicationContext() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.OMDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static OmdbApi provideOmdbAPI(Retrofit retrofit) {
        return retrofit.create(OmdbApi.class);
    }
}
