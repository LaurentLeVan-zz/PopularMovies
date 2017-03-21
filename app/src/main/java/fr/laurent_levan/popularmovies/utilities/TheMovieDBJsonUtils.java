package fr.laurent_levan.popularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.laurent_levan.popularmovies.data.Movie;
import fr.laurent_levan.popularmovies.data.Review;
import fr.laurent_levan.popularmovies.data.Trailer;

/**
 * Created by Laurent on 22/01/2017.
 * Class to get data from JSON
 */

public final class TheMovieDBJsonUtils {

    /**
     *
     * @param context context
     * @param moviesJsonStr JSON Array string
     * @return the list of movies
     * @throws JSONException
     */
    public static ArrayList<Movie> getBasicMovieDataFromJson(Context context, String moviesJsonStr) throws JSONException {

        final String TMDB_RESULTS ="results";
        final String TMDB_POSTER_PATH ="poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_ORIGINAL_TITLE = "original_title";
        final String TMDB_ID = "id";
        final String TMDB_VOTE = "vote_average";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_STATUS_MS = "status_message";
        final String TMDB_STATUS_CODE = "status_code";

        final String TAG = "DetailActivity";

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JSONObject movieJson = new JSONObject(moviesJsonStr);

        if(movieJson.has(TMDB_STATUS_CODE)){
            return null;
        }else {

            ArrayList<Movie> movies = new ArrayList<>();

            JSONArray moviesJSONArray = movieJson.getJSONArray(TMDB_RESULTS);

            JSONObject currentJSONMovie;
            Movie currentObject;
            String posterPath;
            String overview;
            String originalTitle;
            int movieId;
            Float vote;
            String dateStr;
            Date date;

            for (int i = 0; i < moviesJSONArray.length(); i++) {
                currentJSONMovie = moviesJSONArray.getJSONObject(i);
                posterPath = currentJSONMovie.getString(TMDB_POSTER_PATH);
                overview = currentJSONMovie.getString(TMDB_OVERVIEW);
                originalTitle = currentJSONMovie.getString(TMDB_ORIGINAL_TITLE);
                movieId = currentJSONMovie.getInt(TMDB_ID);
                vote = BigDecimal.valueOf(currentJSONMovie.getDouble(TMDB_VOTE)).floatValue();
                dateStr = currentJSONMovie.getString(TMDB_RELEASE_DATE);
                try {
                    date = sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    date = null;
                }
                currentObject = new Movie(movieId, originalTitle, posterPath, overview, vote, date);
                movies.add(currentObject);
            }

            return movies;
        }

    }

    public static ArrayList<Trailer> getTrailersFromJson(Context context, String trailerJsonStr) throws JSONException {

        final String TMDB_RESULTS ="results";
        final String TMDB_KEY = "key";
        final String TMDB_NAME = "name";
        final String TMDB_SITE = "site";
        final String TMDB_STATUS_MS = "status_message";
        final String TMDB_STATUS_CODE = "status_code";

        JSONObject trailerJson = new JSONObject(trailerJsonStr);

        if(trailerJson.has(TMDB_STATUS_CODE)){
            return null;
        }else {

            ArrayList<Trailer> trailers = new ArrayList<>();

            JSONArray trailersJSONArray = trailerJson.getJSONArray(TMDB_RESULTS);

            JSONObject currentJSONTrailer;
            Trailer currentObject;
            String key;
            String name;
            String site;

            for (int i = 0; i < trailersJSONArray.length(); i++) {
                currentJSONTrailer = trailersJSONArray.getJSONObject(i);
               key = currentJSONTrailer.getString(TMDB_KEY);
                name = currentJSONTrailer.getString(TMDB_NAME);
                site = currentJSONTrailer.getString(TMDB_SITE);
                currentObject = new Trailer(key, name, site);
                trailers.add(currentObject);
            }

            return trailers;
        }

    }

    public static ArrayList<Review> getReviewFromJson(Context context, String reviewJsonStr) throws JSONException {

        final String TMDB_RESULTS ="results";
        final String TMDB_REVIEW_ID = "id";
        final String TMDB_REVIEW_AUTHOR = "name";
        final String TMDB_REVIEW_CONTENT = "content";
        final String TMDB_REVIEW_URL = "url";
        final String TMDB_STATUS_MS = "status_message";
        final String TMDB_STATUS_CODE = "status_code";


        JSONObject reviewJson = new JSONObject(reviewJsonStr);

        if(reviewJson.has(TMDB_STATUS_CODE)){
            return null;
        }else {

            ArrayList<Review> reviews = new ArrayList<>();

            JSONArray reviewsJSONArray = reviewJson.getJSONArray(TMDB_RESULTS);

            JSONObject currentJSONReview;
            Review currentObject;
            String id;
            String author;
            String content;
            String url;

            for (int i = 0; i < reviewsJSONArray.length(); i++) {
                currentJSONReview = reviewsJSONArray.getJSONObject(i);
                id = currentJSONReview.getString(TMDB_REVIEW_ID);
                author = currentJSONReview.getString(TMDB_REVIEW_AUTHOR);
                content = currentJSONReview.getString(TMDB_REVIEW_CONTENT);
                url = currentJSONReview.getString(TMDB_REVIEW_URL);
                currentObject = new Review(id , author, content, url);
                reviews.add(currentObject);
            }

            return reviews;
        }

    }


}
