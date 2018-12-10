package com.scrpn.omdb.omdbmovies;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrpn.omdb.omdbmovies.network.ApiClient;
import com.scrpn.omdb.omdbmovies.network.OmdbApi;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();
    public static final String ARG_IMDB_ID = "imdb_id";

    private DetailedMovie detailedMovie;

    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieActor;
    private TextView movieProduction;
    private TextView movieGenre;
    private TextView moviePlot;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_IMDB_ID)) {
            getMovieDetails(getArguments().getString(ARG_IMDB_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        movieTitle = rootView.findViewById(R.id.movie_title);
        movieDate = rootView.findViewById(R.id.movie_date);
        movieActor = rootView.findViewById(R.id.movie_actors);
        movieProduction = rootView.findViewById(R.id.movie_production);
        movieGenre = rootView.findViewById(R.id.movie_genre);
        moviePlot = rootView.findViewById(R.id.movie_plot);

        return rootView;
    }

    private void getMovieDetails(String imdbId) {
        OmdbApi apiService =
                ApiClient.getClient().create(OmdbApi.class);

        Call<DetailedMovie> call = apiService.getMovie(imdbId, BuildConfig.OMDB_API_KEY);
        call.enqueue(new Callback<DetailedMovie>() {
            @Override
            public void onResponse(@NonNull Call<DetailedMovie> call, @NonNull Response<DetailedMovie> response) {
                int statusCode = response.code();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    DetailedMovie movie = response.body();

                    if (movie != null) {
                        detailedMovie = movie;
                        setLayout(movie);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailedMovie> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void setLayout(DetailedMovie movie) {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(movie.getTitle());
        }

        ImageView toolbarImage = (ImageView) activity.findViewById(R.id.toolbar_image);
        if (toolbarImage != null) {
            Picasso.get().load(movie.getPoster()).into(toolbarImage);
        }

        movieTitle.setText(getString(R.string.title, movie.getTitle()));
        movieDate.setText(getString(R.string.date, movie.getReleased()));
        movieActor.setText(getString(R.string.actors, movie.getActors()));
        movieProduction.setText(getString(R.string.production, movie.getProduction()));
        movieGenre.setText(getString(R.string.genre, movie.getGenre()));
        moviePlot.setText(getString(R.string.plot, movie.getPlot()));

    }
}
