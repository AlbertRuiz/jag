package com.jag.movies.Presenter;

import android.content.Intent;

import com.jag.movies.Model.DataSource.MovieFakeDataSource;
import com.jag.movies.Model.DetailModel;
import com.jag.movies.UI.DetailActivity;
import com.jag.movies.UI.IDetailView;
import com.jag.movies.UI.MovieViewModel;
import com.jag.movies.dependencyinjector.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class DetailPresenter {

    private final IDetailView detailView;
    private final DetailModel detailModel;
    private MovieViewModel movie;
    private int movieId;

    @Inject
    public DetailPresenter(IDetailView detailView, DetailModel detailModel) {
        this.detailView = detailView;
        this.detailModel = detailModel;
    }

    public void onStart(Intent intent) {
        getExtrasFromIntent(intent);
        getMovieDataByID();
        movieDataReady();
    }

    private void movieDataReady() {
        detailView.renderCover(movie.getCoverUrl());
        detailView.renderTitle(movie.getTitle());
        detailView.renderOverview(movie.getOverview());
        detailView.renderGenres(movie.getGenresList());
        detailView.renderScore(movie.getVoteAverage());
        detailView.renderReleaseDate(movie.getReleaseDate());
    }

    public void floatingButtonClicked() {
        if (movie.isFavorited()) {
            movie.setFavorite(false);
            detailView.setFloatingButtonNotFavorited();
        } else {
            movie.setFavorite(true);
            detailView.setFloatingButtonFavorited();
        }
    }

    private void getMovieDataByID() {
        movie = new MovieFakeDataSource().getMovieDataById(movieId);
    }

    private void getExtrasFromIntent(Intent intent) {
        if (intent != null) {
            movieId = intent.getIntExtra(DetailActivity.ID_MOVIE, 0);
        }
        else {
            movieId = 0;
        }
    }
}
