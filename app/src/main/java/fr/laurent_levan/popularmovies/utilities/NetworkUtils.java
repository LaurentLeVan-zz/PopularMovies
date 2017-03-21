package fr.laurent_levan.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Laurent on 22/01/2017.
 * Class that contains all the methods to get data from the network
 */

public class NetworkUtils {

    private static final String STATIC_MOVIEDB_URL =
            "https://api.themoviedb.org/3/movie/";

    private static final String MOVIEDB_BASE_URL = STATIC_MOVIEDB_URL;

    private final static String KEY_PARAM = "api_key";

    public static URL buildGetMoviesUrl(String sortByOption) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(sortByOption)
                .appendQueryParameter(KEY_PARAM, fr.laurent_levan.popularmovies.BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildGetTrailersUrl(String movie_id) {

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath("videos")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            }else{
                return null;
            }
        }
        finally{
            urlConnection.disconnect();
        }
    }
}
