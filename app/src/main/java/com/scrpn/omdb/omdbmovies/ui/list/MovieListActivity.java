package com.scrpn.omdb.omdbmovies.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.scrpn.omdb.omdbmovies.AppComponent;
import com.scrpn.omdb.omdbmovies.OmdbMovieApplication;
import com.scrpn.omdb.omdbmovies.R;
import com.scrpn.omdb.omdbmovies.network.model.Movie;
import com.scrpn.omdb.omdbmovies.ui.BaseActivity;
import com.scrpn.omdb.omdbmovies.ui.details.MovieDetailActivity;
import com.scrpn.omdb.omdbmovies.ui.details.MovieDetailFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MovieListActivity extends BaseActivity implements MovieListScreen, OnMovieSelectedListener {

    private static final String TAG = MovieListActivity.class.getSimpleName();

    @Inject
    MovieListPresenter presenter;

    @BindView(R.id.movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.search_string)
    EditText searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OmdbMovieApplication.injector.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        searchString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.refreshItems(s.toString());
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movie_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachScreen(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachScreen();
    }



    @Override
    public void onMovieSelected(String imdbId) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.ARG_IMDB_ID, imdbId);

        startActivity(intent);
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        recyclerView.setAdapter(new MovieRecyclerViewAdapter(movies, MovieListActivity.this));
    }

    @Override
    public void onLoadFailed() {
        Log.e(TAG, getString(R.string.load_fail_message));
    }

    @Override
    protected void injectDependencies(AppComponent injector) {
        injector.inject(this);
    }
}
