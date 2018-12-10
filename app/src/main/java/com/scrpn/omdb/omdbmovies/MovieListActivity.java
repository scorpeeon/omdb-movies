package com.scrpn.omdb.omdbmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.scrpn.omdb.omdbmovies.network.ApiClient;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.Movie;
import com.scrpn.omdb.omdbmovies.network.model.SearchResponse;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieSelectedListener {

    private static final String TAG = MovieListActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private EditText searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.movie_list);
        searchString = findViewById(R.id.search_string);

        searchString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshItems(s.toString());
            }
        });
    }

    void refreshItems(String searchString) {
        OmdbApi apiService =
                ApiClient.getClient().create(OmdbApi.class);

        Call<SearchResponse> call = apiService.getMovies("movie", searchString, BuildConfig.OMDB_API_KEY);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                int statusCode = response.code();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    List<Movie> movies = response.body() != null ? response.body().getSearch() : null;

                    if (movies != null) {
                        recyclerView.setAdapter(new MovieRecyclerViewAdapter(movies, MovieListActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onMovieSelected(String imdbId) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.ARG_IMDB_ID, imdbId);

        startActivity(intent);
    }
}
