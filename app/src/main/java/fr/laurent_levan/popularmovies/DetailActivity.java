package fr.laurent_levan.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailActivity extends AppCompatActivity {

    private static final String imageBaseUrl = "https://image.tmdb.org/t/p/w185";

    private static final Integer NUMBER_OF_STAR = 5;

    private static final String TAG = "DetailActivity";

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Movie mMovie;
    private TextView mTitleDisplay;
    private ImageView mPosterImageView;
    private TextView mSynopsisDisplay;
    private RatingBar mRatingDisplay;
    private TextView mReleaseDateDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        mPosterImageView = (ImageView) findViewById(R.id.iv_movie_poster);
        mSynopsisDisplay = (TextView) findViewById(R.id.tv_movie_synopsis);
        mRatingDisplay = (RatingBar)findViewById(R.id.rb_movie_rating);
        mReleaseDateDisplay = (TextView) findViewById(R.id.tv_movie_release_date);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null){
            if(intentThatStartedThisActivity.hasExtra("myMovieObj")){
                mMovie = intentThatStartedThisActivity.getParcelableExtra("myMovieObj");
                mTitleDisplay.setText(mMovie.getOriginalTitle());
                Picasso.with(this).load(imageBaseUrl + mMovie.getPosterSrc()).into(mPosterImageView);
                mSynopsisDisplay.setText(mMovie.getPlotSynopsis());
                mRatingDisplay.setNumStars(NUMBER_OF_STAR);
                mRatingDisplay.setIsIndicator(true);
                mRatingDisplay.setRating(mMovie.getUserRating()/2);
                mReleaseDateDisplay.setText(sdf.format(mMovie.getReleaseDate()));
            }
        }


    }
}
