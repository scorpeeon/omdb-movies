package com.scrpn.omdb.omdbmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scrpn.omdb.omdbmovies.AppComponent;
import com.scrpn.omdb.omdbmovies.OmdbMovieApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(OmdbMovieApplication.injector);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);
    }

    protected abstract int getLayoutResource();

    protected abstract void injectDependencies(AppComponent injector);

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
