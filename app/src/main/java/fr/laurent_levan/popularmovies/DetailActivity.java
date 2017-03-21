package fr.laurent_levan.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.laurent_levan.popularmovies.data.Movie;
import fr.laurent_levan.popularmovies.data.Trailer;
import fr.laurent_levan.popularmovies.databinding.ActivityDetailBinding;
import fr.laurent_levan.popularmovies.utilities.NetworkUtils;
import fr.laurent_levan.popularmovies.utilities.TheMovieDBJsonUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String imageBaseUrl = "https://image.tmdb.org/t/p/w185";

    private static final Integer NUMBER_OF_STAR = 5;

    private static final String TAG = "DetailActivity";

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private ActivityDetailBinding mDetailBinding;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null){
            if(intentThatStartedThisActivity.hasExtra("myMovieObj")){
                mMovie = intentThatStartedThisActivity.getParcelableExtra("myMovieObj");
                mDetailBinding.primaryInfo.tvMovieTitle.setText(mMovie.getOriginalTitle());
                Picasso.with(this).load(imageBaseUrl + mMovie.getPosterSrc()).into(mDetailBinding.primaryInfo.ivMoviePoster);
                mDetailBinding.primaryInfo.tvMovieSynopsis.setText(mMovie.getPlotSynopsis());
                mDetailBinding.primaryInfo.rbMovieRating.setNumStars(NUMBER_OF_STAR);
                mDetailBinding.primaryInfo.rbMovieRating.setIsIndicator(true);
                mDetailBinding.primaryInfo.rbMovieRating.setRating(mMovie.getUserRating()/2);
                mDetailBinding.primaryInfo.tvMovieReleaseDate.setText(sdf.format(mMovie.getReleaseDate()));
            }
        }


    }

    public class FetchTrailerTask extends AsyncTask<String, Void, ArrayList<Trailer>> {

        @Override
        protected ArrayList<Trailer> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String movie_id = params[0];

            URL trailersRequestUrl = NetworkUtils.buildGetTrailersUrl(movie_id);

            try {
                String jsonTrailerResponse = NetworkUtils.getResponseFromHttpUrl(trailersRequestUrl);

                ArrayList<Trailer> trailerList = TheMovieDBJsonUtils
                        .getTrailersFromJson(DetailActivity.this, jsonTrailerResponse);

                return trailerList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            if(trailers != null) {
                //TODO: Display trailers
            }
        }
    }
}
