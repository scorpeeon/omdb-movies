package com.scrpn.omdb.omdbmovies.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scrpn.omdb.omdbmovies.AppComponent;
import com.scrpn.omdb.omdbmovies.OmdbMovieApplication;
import com.scrpn.omdb.omdbmovies.R;
import com.scrpn.omdb.omdbmovies.network.model.DetailedMovie;
import com.scrpn.omdb.omdbmovies.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class MovieDetailFragment extends BaseFragment implements MovieDetailsScreen {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();
    public static final String ARG_IMDB_ID = "imdb_id";

    private DetailedMovie detailedMovie;

    @Inject
    MovieDetailsPresenter presenter;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_date)
    TextView movieDate;

    @BindView(R.id.movie_actors)
    TextView movieActor;

    @BindView(R.id.movie_production)
    TextView movieProduction;

    @BindView(R.id.movie_genre)
    TextView movieGenre;

    @BindView(R.id.movie_plot)
    TextView moviePlot;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_IMDB_ID)) {
            presenter.getMovieDetails(getArguments().getString(ARG_IMDB_ID));
        }
    }

    @Override
    protected void injectDependencies(final AppComponent injector) {
        injector.inject(this);
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

    private void setLayout(DetailedMovie movie) {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(movie.getTitle());
        }

        ImageView toolbarImage = (ImageView) activity.findViewById(R.id.toolbar_image);
        if (toolbarImage != null) {
            Glide.with(this).load(movie.getPoster()).into(toolbarImage);
        }

        movieTitle.setText(getString(R.string.title, movie.getTitle()));
        movieDate.setText(getString(R.string.date, movie.getReleased()));
        movieActor.setText(getString(R.string.actors, movie.getActors()));
        movieProduction.setText(getString(R.string.production, movie.getProduction()));
        movieGenre.setText(getString(R.string.genre, movie.getGenre()));
        moviePlot.setText(getString(R.string.plot, movie.getPlot()));

    }

    @Override
    public void onMovieLoaded(DetailedMovie movie) {
        detailedMovie = movie;
        setLayout(movie);
    }

    @Override
    public void onLoadFailed() {
        Log.e(TAG, getString(R.string.load_fail_message));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.movie_detail;
    }
}
