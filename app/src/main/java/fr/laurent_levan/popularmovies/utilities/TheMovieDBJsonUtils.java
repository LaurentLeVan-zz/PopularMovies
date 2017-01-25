package fr.laurent_levan.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.laurent_levan.popularmovies.Movie;

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
}
